import groovy.io.FileType

def list = []

def dir = new File("C:/Users/RegCorp 95/Documents/groovydir/inputdir")
def keys = [] as Set, fileCount = 1, dupes = [], output = new File("C:/Users/RegCorp 95/Documents/groovyoutput3/out"+"_"+fileCount+".csv"),lineNo = 1
def writer = output.newWriter("UTF-8", true)
dir.eachFileRecurse (FileType.FILES) { file ->
list << file.getAbsolutePath()
  
}

list.each {
    println 'Inside list.each'
    println it
    def inputFile = new File(it), line
  
  inputFile.withReader { reader ->
 while ((line = reader.readLine())!=null ) {
     
     def key = ""
     try{
     def temp = line.split(/,(?=(?:(?:[^"]*"){2})*[^"]*$)/)
     
      if(temp.size()>=7) key  = temp[7]
     }catch(Exception ex){println 'moving on after exception'}
    
    if(key==""){
        lineNo++
        writer.write(line+'\n')
       
    }
        
   // println key
    if (keys.contains(key)) dupes.add(key)
    else {
        keys.add(key)
        lineNo++
        writer.write(line+'\n') 
       
    }
    if (lineNo % 100000 == 0) println 'written '+lineNo
    if (lineNo % 300000 == 0) {
        fileCount++
        writer.close()
        println 'New file '+fileCount
        output = new File("C:/Users/RegCorp 95/Documents/groovyoutput3/out"+"_"+fileCount+".csv")
        lineNo=1
        writer = output.newWriter("UTF-8", true)        
    }
    
}

}

}