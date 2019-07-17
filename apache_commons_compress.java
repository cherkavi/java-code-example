import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
  
    private String createZip(String[] files, String returnFileName) throws Exception {
		if( (files!=null) && (files.length>0) ){
			try{
				
				BufferedInputStream inputStream = null;
				// ZipArchiveOutputStream, JarArchiveOutputStream 
				ArchiveOutputStream archiv = new ZipArchiveOutputStream(new FileOutputStream(returnFileName));
				byte data[] = new byte[READ_WRITE_BUFFER_SIZE];
				ArchiveEntry archivEntry =null;
				
				int count;
				// Iterate over the file list and add them to the zip file.
				for (int counter=0; counter <files.length; counter++){
					// archivEntry=archiv.createArchiveEntry(new File(files[counter]), extractFileName(files[counter]));
					archivEntry=new ZipArchiveEntry(extractFileName(files[counter]));
					LOG.debug("add name:"+archivEntry.getName());
					archiv.putArchiveEntry(archivEntry);
					inputStream = new BufferedInputStream(new FileInputStream(files[counter]), READ_WRITE_BUFFER_SIZE);
					while((count = inputStream.read(data,0,READ_WRITE_BUFFER_SIZE)) != -1){archiv.write(data,0,count);}
					inputStream.close();
					archiv.closeArchiveEntry();
					archiv.flush();
				 }
				 archiv.finish();
			}catch(IOException ex){
				LOG.error(this, "create Zip archiv exception:"+ex.getMessage());
				throw ex;
			}
		}else{
			LOG.error(this, "no such files for sent fia E-Mail for ZIP compressing ");
			throw new Exception("no such files for sent via E-mail ");
		}
		return returnFileName;

    }

