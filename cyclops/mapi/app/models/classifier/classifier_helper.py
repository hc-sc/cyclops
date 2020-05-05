import torch
import torch.nn as nn
import torch.nn.functional as F
import pdb
import numpy as np
#from tqdm import tqdm

import torch.backends.cudnn as cudnn
from torch.optim.lr_scheduler import MultiStepLR

from torchvision.utils import make_grid
from torchvision import datasets, transforms, utils
import torch.utils.data as utils
from torch.utils.data import Dataset, DataLoader

from torch.autograd import Variable
import time
import os
import copy
import math
import random,pickle
import matplotlib.pyplot as plt

DATA_DIR='/data'


with open(f'{DATA_DIR}/partitioned_final_data/primary_word_data_pretrained/pre_pd_word_to_word_embd_dictionary.pickle', 'rb') as handle:
    pre_pd_word_to_word_embd_dictionary = pickle.load(handle)
with open(f'{DATA_DIR}/partitioned_final_data/primary_word_data/pd_word_to_ind_dictionary.pickle', 'rb') as handle:
    pd_word_to_ind_dictionary = pickle.load(handle)
with open(f'{DATA_DIR}/partitioned_final_data/primary_word_data/pd_word_to_word_embd_dictionary.pickle', 'rb') as handle:
    pd_word_to_word_embd_dictionary = pickle.load(handle)
with open(f'{DATA_DIR}/partitioned_final_data/character_dict/char_index.pickle', 'rb') as handle:
    char_index = pickle.load(handle)
with open(f'{DATA_DIR}/classifier/word_dict.pickle', 'rb') as handle:
    classifier_word_dict = pickle.load(handle)

def classify_images(test_custom_dataset,graph_ie_df):


    annotated_graphie_label_dict ={6:"npn_number",
                                   5:"productname",
                                   4:"companyname",
                                   3:"claim",
                                   2:"dosage",
                                   1:"ingredients",
                                   0:"unknown"}


    original_labels_lookup = {'company_address': 0,
     'risk': 1,
     'medicinal': 2,
     'dose': 3,
     'claims': 4,
     'company_names': 5,
     'nonmedicinal': 6,
     'product_names': 7,
     'npn': 8}

    # this maps the annotated indices used to the ones we used for training the above model
    annotated_label_translation_dict = {1:0, 2:1, 3:2, 4:3, 5:4, 7:5, 8:6}



    NUM_CLASSES = 7

    args = {
        'char_vocab_size' : len(char_index),
        'word_vocab_size': len(pd_word_to_ind_dictionary),
        'vocab_list': pd_word_to_word_embd_dictionary.keys(),
        'pre_pd_word_to_word_embd_dictionary': pre_pd_word_to_word_embd_dictionary,
        'test_dataset':test_custom_dataset,
        'num_classes': NUM_CLASSES,
        'char_emb_dim':32,
        'char_hidden_dim':64,
        'word_emb_dim':200,
        'word_hidden_dim':256,
        'BATCH_SIZE':1,
        'LEARNING_RATE':0.03,
        'NUM_EPOCHS':200,
        'model_dir' : f'{DATA_DIR}/classifier/Train_and_Test_with_AnnotatedDataset_s20_epoch27_lr_0.03.pth',
        'plt_dir':'plot/test_1/'

    }


    '''
    Model code
    '''
    class Model(nn.Module):
        def __init__(self, args, s=20.):
            super(Model, self).__init__()

            # housekeeping variables/constants
            self.char_vocab_size = args['char_vocab_size']
            self.word_vocab_size = args['word_vocab_size']
            self.num_classes = args['num_classes']
            self.char_emb_dim = args['char_emb_dim']
            self.char_hidden_dim = args['char_hidden_dim']
            self.word_emb_dim = args['word_emb_dim']
            self.word_hidden_dim = args['word_hidden_dim']
            self.vocab_list = args['vocab_list']
            self.pre_pd_word_to_word_embd_dictionary = args['pre_pd_word_to_word_embd_dictionary']
            self.s = s

            # network layers
            self.char_embedding = nn.Embedding(self.char_vocab_size, self.char_emb_dim)
            self.char_bilstm = nn.LSTM(input_size=self.char_emb_dim, hidden_size=self.char_hidden_dim, batch_first=True, bidirectional=True)

            # Initialize word_embeddings
            weights = self.initialize_word_embedding_dict(pre_pd_word_to_word_embd_dictionary)
            self.word_embedding = nn.Embedding(self.word_vocab_size, self.word_emb_dim)
            self.word_embedding.weight.data.copy_(torch.from_numpy(weights).to(device))


            self.word_bilstm = nn.LSTM(input_size=self.word_emb_dim+2*self.char_hidden_dim, hidden_size=self.word_hidden_dim, batch_first=True, bidirectional=True)

            self.fc = nn.Linear(in_features=self.word_hidden_dim*2, out_features=self.num_classes, bias=False)


        def initialize_word_embedding_dict(self, pretrained_dict):
            matrix_len = len(self.vocab_list)
            weights_matrix = np.zeros((matrix_len, 200))
            emb_dim = 200

            for i, word in enumerate(self.vocab_list):
                if i != 0:
                    try:
                        weights_matrix[i] = pretrained_dict[word]
                    except KeyError:
                        weights_matrix[i] = np.random.uniform(low=-1, high=1, size=(self.word_emb_dim,))
            return weights_matrix

        def init_word_embedding(self, pretrained_dict):
            '''
            :pretrained_dict: is a dict mapping word indices to the pretrained embeddings
            '''
            #N = len(pretrained_dict)
            #for i in range(1,N+1):
            #    self.word_embedding[i] = pretrained_dict[i]
            pass

        def init_char_hidden(self):
            pass

        def init_word_hidden(self):
            pass

        def forward(self, packed_char_data, char_seqs, sorted_indices ,unsorted_indices, word_x):
            '''
            :packed_char_data: packed character data (assumes batch size of 1, hence the number of words acts as the batch_size)
            :char_seqs: packed char sequence lengths
            :word_x: input of shape (batch_size, seq_length, 1) *** seq_length = number of words for this input
            :y: labels of shape (batch_size, seq_length, 1) - seq_length = number of words for this input, these are class labels
            '''

            # get char embeddings
            char_out = self.char_embedding(packed_char_data.to(device))        # shape (number inputs, embedding_dim)
            char_packed = torch.nn.utils.rnn.PackedSequence(char_out, torch.tensor(char_seqs),sorted_indices = sorted_indices ,unsorted_indices = unsorted_indices)  # pack the sequence for the biLstm
            out, (h,c) = self.char_bilstm(char_packed)     # get the word embeddings from the characters, hence we want h
            # h = h[:,unsorted_indices,:]
            h = h.resize(h.shape[1], h.shape[0]*h.shape[2])

            # get word embeddings
            word_x = self.word_embedding(word_x)
            # concatenate
            word_combined = torch.cat((h, word_x), dim=1)
            word_combined = word_combined.resize(1, word_combined.shape[0], word_combined.shape[1]) # add in the batch size dimension

            # word bistm
            out, (h,c) = self.word_bilstm(word_combined)
            out = out.resize(out.shape[1],out.shape[2])  # remove sequence dimension

            #out = self.fc(out)
            out = F.linear(F.normalize(out), F.normalize(self.fc.weight))
            out *= self.s

            return out



    class Character_PadSequence:
        def __call__(self, batch):
            #sorted_batch = sorted(char_batch, key=lambda x: x.shape[0], reverse=True)
            #sorted_batch = sorted(char_batch, key=lambda x: len(x), reverse=True)
            #sorted_batch = sorted(batch, key=lambda x: len(x['char_input']), reverse=True)
            '''
            NOTE: batch[0] extracts the index 0 of the batch.... so if this is batch_size=1, this is fine, otherwise,
            this method only returns the first element of a batch....
            '''
            sequences = [torch.tensor(x) for x in batch[0]['char_input']]
            sequences_padded = torch.nn.utils.rnn.pad_sequence(sequences, batch_first=True)
            lengths = torch.LongTensor([len(x) for x in sequences])

            return sequences_padded, lengths, batch[0]['word_input']




    class TestDataset(Dataset):

        def __init__(self, data, transform=None, label_translation_dict=None):

            self.transform = transform
            self.data = data
            self.label_translation_dict = label_translation_dict

            self.char_inputs = {i: self.data[i][0] for i in range(len(self.data))}
            self.word_inputs = {i: self.data[i][1] for i in range(len(self.data))}
            self.words = {i: self.data[i][2] for i in range(len(self.data))}

        def __len__(self):
            return len(self.data)

        def __getitem__(self, idx):
            sample = {'char_input': self.char_inputs[idx], 'word_input':self.word_inputs[idx], 'words': self.words[idx]}

            if self.transform:
                sample = self.transform(sample)

            return sample

    def test_epoch(model, test_dataloader, device):
        model.eval()
        correct = 0.
        word_count = 0.
        preds = []
        labels = []


        with torch.no_grad():
            for batch_idx, data in enumerate(test_dataloader):
                try:
                    padded_char_seq_batch,  char_seq_lens, word_seq = data
                    packed_char_seq_batch = torch.nn.utils.rnn.pack_padded_sequence(padded_char_seq_batch,
                                                                                        lengths = char_seq_lens,
                                                                                        batch_first=True,
                                                                                        enforce_sorted=False)
                    packed_char_data, char_seqs, sorted_indices ,unsorted_indices = packed_char_seq_batch
                    # we will send the packed_char_data to the model and process, then pass to GPU (cuda) afterwards
                    word_seq = torch.tensor(word_seq).to(device)

                    out = model(packed_char_data, char_seqs, sorted_indices.to(device) ,unsorted_indices.to(device), word_seq)
                    pred = out.max(1, keepdim=True)[1] # get the index of the max log-probability
                    word_count += out.shape[0]
                    pred = pred.detach().cpu().numpy()

                    preds.append(pred)
                except:
                    print("\tError predicting")
        assert (len(preds) == len(test_dataloader))
        return [[annotated_graphie_label_dict[a.item()] for a in sublist] for sublist in preds]

    def check_class_dict(tokens,classifier_word_dict):
        word_class_match = []
        for word in tokens:
            class_match = []
            for class_value in classifier_word_dict.keys():
                if len(set(word) - classifier_word_dict[class_value]) == 0:
                    class_match.append(class_value)
            word_class_match.append(class_match)
        return word_class_match

    PRED_TEXT_TO_NUM = {"npn_number": 8,
                        "productname": 7,
                        "companyname": 5,
                        "claim": 4,
                        "dosage": -1,
                        "ingredients": 2,
                        "unknown": -1
                        }

    def summarize_graph(row):
        ocr_words = []
        for word, corrected_word, position, bigram_match_class, matched_class, symspell_matched_class, prediction in zip(
                row['tokens'], row['symspelled_tokens'], row['x_y'], row['bigram_match_class'],
                row['ocr_word_match'], row['symspell_word_match'], row['classifier']):
            ocr_words.append({
                "raw_ocr_word": word,
                "corrected_ocr_word": corrected_word,
                "coordinates": position,
                "filename": row['file_name'],
                "classifier_class": PRED_TEXT_TO_NUM[prediction],
                "ocr_match_class": matched_class,
                "symspell_match_class": symspell_matched_class,
                "bigram_match_class": bigram_match_class,
                "_flagged_schedule_a": None,
                "_injectable_flag": None})
        return ocr_words

    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    cudnn.benchmark = True
    test_dataset = TestDataset(args['test_dataset'], transform=None, label_translation_dict=annotated_label_translation_dict)


    random.seed(100)
    np.random.seed(200)
    torch.manual_seed(0)
    test_dataloader = torch.utils.data.DataLoader(test_dataset, batch_size = args['BATCH_SIZE'],
                                                  shuffle=False, collate_fn = Character_PadSequence())

    model = Model(args)
    model.load_state_dict(torch.load(args['model_dir'],map_location=torch.device('cpu')))
    model.to(device)
    # Print the number of parameters in the model
    parameters = filter(lambda p: p.requires_grad, model.parameters())
    params = sum([p.numel() for p in parameters])
    preds = test_epoch(model, test_dataloader, device)
    graph_ie_df['classifier'] = preds

    #graph_ie_df['symspell_word_match'] = graph_ie_df.symspelled_tokens.apply(lambda tokens: check_class_dict(tokens,classifier_word_dict))
    #graph_ie_df['ocr_word_match'] = graph_ie_df.tokens.apply(lambda tokens: check_class_dict(tokens,classifier_word_dict))
    output = []
    for idx, val in graph_ie_df.groupby('file_name'):
        graph_summaries = val.apply(summarize_graph, axis=1)
        output.append(graph_summaries.tolist())

    return output
