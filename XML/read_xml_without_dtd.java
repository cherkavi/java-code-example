	private Document getXmlByPathWithoutDtd(InputStream inputStream) throws Exception{
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();

	        builder.setEntityResolver(new EntityResolver() {

	            @Override
	            public InputSource resolveEntity(String publicId, String systemId)
	                    throws SAXException, IOException {
	                System.out.println("Ignoring " + publicId + ", " + systemId);
	                return new InputSource(new StringReader(""));
	            }
	        });
	        return builder.parse(inputStream);	
	  }
	
	private Document getXmlByPath(InputStream inputStream) throws Exception{
        Document return_value=null;
		javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
        document_builder_factory.setValidating(false);
        document_builder_factory.setSchema(null);
        try {
            javax.xml.parsers.DocumentBuilder parser=document_builder_factory.newDocumentBuilder();
            return_value=parser.parse(inputStream);
        }catch(Exception ex){
        	System.err.println("XmlExchange.java ERROR");
        	throw ex;
        }
		return return_value;
	}

