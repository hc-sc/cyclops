# -*- coding: utf-8 -*-
from models.OCR.RECOG_pkg.utils import CTCLabelConverter, AttnLabelConverter
from models.OCR.RECOG_pkg.dataset import AlignCollate, CroppedDataset
from models.OCR.RECOG_pkg.model import Model as RECOG_Model

import torch

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
def load_recognition_model(args):
    """ model configuration """
    if 'CTC' in args.Prediction:
        converter = CTCLabelConverter(args.character)
    else:
        converter = AttnLabelConverter(args.character)
    args.num_class = len(converter.character)

    if args.rgb:
        args.input_channel = 3
    recog_model = RECOG_Model(args)
    print('model input parameters', args.imgH, args.imgW, args.num_fiducial, args.input_channel, args.output_channel,
          args.hidden_size, args.num_class, args.batch_max_length, args.Transformation, args.FeatureExtraction,
          args.SequenceModeling, args.Prediction)
    recog_model = torch.nn.DataParallel(recog_model).to(device)

    # load model
    print('loading pretrained model from %s' % args.recognition_model)
    recog_model.load_state_dict(torch.load(args.recognition_model, map_location=torch.device('cpu')))

    return recog_model, converter

def recognize_text_in_imgs(imgs, recog_model, recog_converter, args):
    AlignCollate_demo = AlignCollate(imgH=args.imgH, imgW=args.imgW, keep_ratio_with_pad=args.PAD)

    imgs_loader = torch.utils.data.DataLoader(
        CroppedDataset(root=imgs, opt=args),
        batch_size=args.batch_size,
        shuffle=False,
        num_workers=int(args.workers),
        collate_fn=AlignCollate_demo, pin_memory=True)

    return recognize_text(imgs_loader, recog_model, recog_converter, args)


def recognize_text(imgs_loader, recog_model, recog_converter, args):
    # predict
    pred_texts = []
    with torch.no_grad():
        for image_tensors in imgs_loader:

            batch_size = image_tensors.size(0)
            image = image_tensors.to(device)
            # For max length prediction
            length_for_pred = torch.IntTensor([args.batch_max_length] * batch_size).to(device)
            text_for_pred = torch.LongTensor(batch_size, args.batch_max_length + 1).fill_(0).to(device)

            if 'CTC' in args.Prediction:
                preds = recog_model(image, text_for_pred)

                # Select max probabilty (greedy decoding) then decode index to character
                preds_size = torch.IntTensor([preds.size(1)] * batch_size)
                _, preds_index = preds.permute(1, 0, 2).max(2)
                preds_index = preds_index.transpose(1, 0).contiguous().view(-1)
                preds_str = recog_converter.decode(preds_index.data, preds_size.data)

            else:
                preds = recog_model(image, text_for_pred, is_train=False)

                # select max probabilty (greedy decoding) then decode index to character
                _, preds_index = preds.max(2)
                preds_str = recog_converter.decode(preds_index, length_for_pred)

            for img_name, pred in enumerate(preds_str):
                if 'Attn' in args.Prediction:
                    pred = pred[:pred.find('[s]')]  # prune after "end of sentence" token ([s])

                pred_texts.append(pred)
    return pred_texts
