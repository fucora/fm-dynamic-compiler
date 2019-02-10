import com.fm.data.trade.dynamic.OApiTransformer;

public class Tes extends OApiTransformer {
	public void hello(){
		System.out.println(sd());
	}
	@Override
    public String sd() {

        return "ds";
    }
	public Td getTd(){
		return new Td();
	}
	public class Td{
		private String ds;
	}
}
