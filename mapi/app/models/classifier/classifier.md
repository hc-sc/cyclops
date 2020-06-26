
## Classifier  
A neural network-based model was build to classify different sections of text retrieved from the natural health product images. The implementation is based on the PyTorch library.  

<div align="center"><img src="documentation_images/classifier/model.png"  width="500" height="700"> </div>

To create this model, we used bi-directional LSTM with embedding done on _two_ levels of granularity: on the character and word levels. Results presented by the authors of _Cross-type Biomedical Named Entity Recognition with Deep Multi-task Learning (Bioinformatics'19)_ , this approach often yields strong results for our use case.  

**1. Datasets** 
As part of this classifier, we have used two different datasets:  
- LNHP Dataset  
- Annotated-OCR Dataset  
  
_**LNHP Database**_: The Licensed Natural Health Products Database contains information about natural health products that have been issued a product license by Health Canada. These products with a licence have been assessed by Health Canada and found to be safe, effective and of high quality under their recommended conditions of use. This Licensed Natural Health Products Database is managed by Health Canada and includes information on **licensed** natural health products. For every licensed product listed in this database, the following details are provided:  
- product name  
- product licence holder  
- Natural Product Number (NPN) or Homeopathic Medicine Number (DIN-HM)  
- product's medicinal ingredients  
- product's non-medicinal ingredients  
- product's dosage form  
- product's recommended use or purpose (i.e. its health claim or indication)  
- risk information associated with the product's use (i.e. cautions, warnings, contra-indications and known adverse reactions)  
  
Once we have extracted all the data from this database, we have tokenized, lowercased each record and created a master list of unique words that will be useful to create our word embedding dictionary. Below is a distribution of data points with their respective classes.  

<div float="left">
  <img src="models/classifier/documentation_images/class_distribution.png" width="45%" />
  <img src="models/classifier/documentation_images/data.png " width="45%" /> 
</div>

_**Annotated-OCR Dataset**_: To build this dataset, all the images had to undergo the OCR text detection and recognition process which provides the extracted text as well as its corresponding coordinates for it. This is then used to match with the Annotated dataset where the inspectors have used the custom annotation tool to label all the available images. Once the class membership for each token is determined, the GraphIE algorithm proposed in _GraphIE: A Graph-Based Framework for Information Extraction_ is used to build subgraphs. These subgraphs will be providing more context to the classifier in the form of mapped sentences. These labeled sentences are formated into a custom JSON format with each record representing a single subgraph for our model.  
 
<div align="center"><img src="models/classifier/documentation_images/annotated_ocr.png"  width="50%"> 
</div>

**2. Word Level Embedding**  
  
Let’s now focus on the first embedding layer — the word embedding. Words present in a sentence are subjected to the embedding process, where they are converted into vectors of numbers. These vectors capture the grammatical function (_syntax_) and the meaning (_semantics_) of the words, enabling us to perform various mathematical operations on them.  
  
To create our word embedding dictionary, We first initialized a word embedding matrix with pre-trained word vectors from Pyysalo et al., 2013. These word vectors are trained using the skip-gram model on the PubMed abstracts together with all the full-text articles from PubMed Central (PMC) and a Wikipedia dump. Then we have tokenized our entire dataset (created from NPH Database) and mapped common words present in our dataset with embeddings from our pre-trained matrix. Any words not present, where assigned a random 200 dimension vector and the _**UNK**_ token was assigned to index 0.  
  
Our final dictionary has been structured as follows:  

<div float="left">
  <img src="documentation_images/classifier/word_dictionary.png" width="45%" />
  <img src="documentation_images/classifier/embedding.png " width="45%" /> 
</div>

During the prediction time, If we haven’t seen a word, it is assigned to _**UNK**_ encoding and the model has to infer it’s meaning by it’s surrounding words. Often word postfix or prefix contains a lot of information about the meaning of the word. Using this information is very important if you deal with texts that contain a lot of rare words and you expect a lot of unknown words at inference time.  
  
**3. Character Level Embedding**  
  
To provide more context and deal with Out-of-Vocabulary or spelling error scenarios, we also use character-level information. Character embeddings are used to encode every word to a vector resulting in a sequence of characters representing each word in a sentence. This vector is fed to another LSTM together with the learned word embedding.  
  
**4. Model**  
We have used a stratified sampling method where we have an equal distribution of 8 classes in both training and test set. The training had 70% of the records with 156,375 data points whereas test had the remaining 30% with 67018 data points. Our LSTM model then collectively trains different categorical records to build a unified model.

**5. Parameters used** 
//To be done

**6. Evaluation** 
//To be done
