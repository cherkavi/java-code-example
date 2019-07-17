	private static Document getXmlFromInputStream(InputStream inputStream) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        // установить непроверяемое порождение Parser-ов
        document_builder_factory.setValidating(false);
        // получение анализатора
        javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
        parser.setEntityResolver(new EntityResolver(){
			public InputSource resolveEntity(String arg0, String arg1)throws SAXException, IOException {
				return new InputSource(new StringReader(""));
			}
        });
        return_value=parser.parse(inputStream);
		return return_value;
	}

