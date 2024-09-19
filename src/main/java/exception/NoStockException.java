package exception;


//忘了用没用了
public class NoStockException extends RuntimeException {

    private String sellID;

    public NoStockException(String sellID) {
        super(sellID + "库存不足");
    }


}
