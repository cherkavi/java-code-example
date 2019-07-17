class StringTokenizer implements Enumeration{
    private Vector v=new Vector();
    private int counter=0;
    StringTokenizer(String text,String delimeter){
        int index=0;
        while((index=(text.indexOf(delimeter)))>0){
            v.addElement(text.substring(0,index));
            text=text.substring(index+delimeter.length());
        }
        if(text.trim().length()!=0){
        	v.addElement(text);
        }
    }

    public boolean hasMoreElements() {
        return (counter<v.size());
    }

    public Object nextElement() {
        return v.elementAt(counter++);
    }
    public int countTokens(){
        return v.size();
    }

    public boolean hasMoreTokens() {
        return (counter<v.size());
    }

    public String nextToken() {
        return (String)v.elementAt(counter++);
    }
}
