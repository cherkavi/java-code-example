import java.util.Date;
import java.text.SimpleDateFormat;

object ScalaApplicationExample {
  def main(args : Array[String]):Unit={
    val now=new Date;
    val df=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    println("Now is time: "+(df format now));
  }
  
}
