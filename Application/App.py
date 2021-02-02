import streamlit as st
import spacy
from textblob import TextBlob
from gensim.summarization import summarize
# from sumy.parsers.plaintext import PlainTextParser
# from sumy.nlp.tokenizers import Tokenizer
# from sumy.summarizers.lex_rank import LexRankSummarizer
import pandas as pd
import io
from io import StringIO
import csv


# def sumyTopicModel(docx):
# 	parser = PlainTextParser.from_string(docx, Tokenizer("English"))
# 	lex_summarizer = LexRankSummarizer()
# 	summary = lex_summarizer(parser.document, 3)
# 	summaryList = [str(sentence) for sentence in summary]
# 	result = ' '.join(summaryList)
# 	return result

# def textAnalyzer(text):
# 	nlp = spacy.load('en')
# 	docx = nlp(text)
	
# 	tokens = [ token.text for token in docx]
# 	allData = [('"Tokens":{},\n"Lemma":{}'.format(token.text, token.lemma_)) for token in docx]
# 	return allData 

# def entityAnalyzer(text):
# 	# nlp = spacy.load('en')
# 	docx = nlp(text)
# 	tokens = [ token.text for token in docx]
# 	entities = []
# 	allData = []
# 	for entity in docx.ents: 
# 		if entity.label_ in ['LOC', 'GPE']:
# 			entities.append((entity.text, entity.label_))
# 	if len(entities) > 0:		
# 		allData = [('"Tokens":{},\n"Entities":{}'.format(tokens, entities))]
# 	return allData 

class Analyzer:  
    def __init__(self, text, entities, sentiment, label):  
        self.text = text  
        self.entities = entities
        self.sentiment = sentiment
        self.label = label 

@st.cache
def entityAnalyzer(df, outputOptions):
	nlp = spacy.load('en')
	allData = []
	for index, row in df.iterrows():
		text = row["text"]
		docx = nlp(text)
		#tokens = [ token.text for token in docx]
		entities = []
		for entity in docx.ents: 
			if entity.label_ in ['LOC', 'GPE']:
				entities.append((entity.text, entity.label_))
		if len(entities) > 0:		
			sentiment = sentimentAnalyzer(text)
			label = ''
			if sentiment.polarity > 0:
				label = 'POS'
			elif sentiment.polarity	< 0:
				label = 'NEG'
			else:
				label = 'NEU'	
			if 	outputOptions == 'Json' or outputOptions == 'CSV':
				#allData.append([('"Text":{},\n"Entities":{},\n"Sentiment":{}, \n"Label":{}'.format(row["text"], entities,sentiment, label))])
				allData.append({"Sentence": text, "Location Entities": entities, "Sentiment": sentiment, "Label": label})
			else:
				allData.append(Analyzer(text, entities,sentiment, label))
	return allData 




def sentimentAnalyzer(text):
	blob = TextBlob(text)
	
	return blob.sentiment 	

# def extractSentences(df):
# 	nlp = spacy.load('en')
# 	nlp.add_pipe(nlp.create_pipe('sentencizer')) # updated
# 	textSentences= []
# 	for index, row in df.iterrows():
# 		doc = nlp(row["text"])
# 		sentences = [sent.string.strip() for sent in doc.sents]
# 		entities = entityAnalyzer(row["text"],nlp)	
		
# 		if len(entities) > 0:	
# 			textSentences.append(('"id":{},\n"text":{},\n"sentenceCount":{},\n"sentences":{},\n"entities":{}'.format(row["id"],row["text"], len(sentences), sentences, entities)))	
# 	return textSentences

def addHeader():
	cols = st.beta_columns(4)
	cols[0].write("**Sentence**")
	cols[1].write("**Location Entities**")
	cols[2].write("**Sentiment**")
	cols[3].write("**Label**")	

def populateTable(textAnalysis):
	addHeader()
	for i in range(1, len(textAnalysis)):
	    cols = st.beta_columns(4)
	    cols[0].write(textAnalysis[i].text)
	    cols[1].write(str(textAnalysis[i].entities))
	    cols[2].write(str(textAnalysis[i].sentiment)[9:])
	    cols[3].write(textAnalysis[i].label)

def populateCSV(textAnalysis, fileName):
	csv_columns = ['Sentence','Location Entities','Sentiment', 'Label']

	csv_file = fileName+"-output.csv"
	try:
	    with open(csv_file, 'w') as csvfile:
	        writer = csv.DictWriter(csvfile, fieldnames=csv_columns)
	        writer.writeheader()
	        for data in textAnalysis:
	            writer.writerow(data)
	    st.success("Output file: **"+ csv_file+"**")        
	except IOError:
	    print("I/O error")
	
def main():
	"""NLP App with StreamLit"""
	st.set_page_config(layout="wide")

	st.title("PADI WEB Data Experiments")

	file = st.file_uploader("Upload a file", type=("csv", "xls","xlsx")) 
	if file:
		uploaded_file = io.TextIOWrapper(file)
		if uploaded_file is not None:
			df = pd.read_csv(uploaded_file)
			output_options = st.selectbox("Choose output format options", ("Json","Data Table", "CSV"))
			if st.button("Analyze"):
				#st.success("Sentence Count:"+ str(len(df.index)))
				# st.table(entityAnalyzer(df))
				st.success("Total Sentence Count: **"+ str(len(df.index))+"**")
				textAnalysis = entityAnalyzer(df, output_options)
				st.success("Sentence with location Entities: **"+str(len(textAnalysis))+"**")
				if output_options == 'Json':
					st.json(textAnalysis)
				elif output_options == 'Data Table':	
					populateTable(textAnalysis)
				elif output_options == 'CSV':	
					populateCSV(textAnalysis, file.name)
				
				#st.table(df)
	#pd.read_csv(file, delimiter='\t')

	#Tokenization
	# if st.checkbox("Show Tokens and Lemma"):
	# 	st.subheader("Tokenize your text")
	# 	message = st.text_area("Enter your text", "Type here")
	# 	if st.button("Analyze"):
	# 		tokens = textAnalyzer(message)
	# 		st.json(tokens)

	#NER
	# if st.checkbox("Show Named Entities"):
	# 	st.subheader("Extract Entities")
	# 	message = st.text_area("Enter your text", "Type here")
	# 	if st.button("Extract"):
	# 		tokens = entityAnalyzer(message)
	# 		st.json(tokens)
	# #Sentiment Analysis
	# if st.checkbox("Show Sentiment Analysis"):
	# 	st.subheader("Sentiment of your text")
	# 	message = st.text_area("Enter your text", "Type here")
	# 	if st.button("Analyze"):
	# 		st.success(sentimentAnalyzer(message))


	# #Topic Modeling
	# if st.checkbox("Show topic modeling"):
	# 	st.subheader("Extract topics from your text")
	# 	message = st.text_area("Enter your text", "Type here")
	# 	topic_modeling_options = st.selectbox("Choose topic modeling options", ("gensim","sumy"))
	# 	if st.button("Extract"):
	# 		if topic_modeling_options == "gensim":
	# 			st.text("Using Gensim")
	# 			summaryResult = summarize(message)
	# 		# elif topic_modeling_options == "sumy":
	# 		# 	st.text("Using Sumy")
	# 		# 	summaryResult = sumyTopicModel(message)
	# 		else:
	# 			st.warning("Using default topic modeler Gensim")

	# 		st.success(summaryResult)	


if __name__ == '__main__':
	main()	