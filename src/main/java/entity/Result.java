package entity;


//前端返回数据所需要的格式，必须！！！！！！！！！！！！！！！
//微服务间无所谓
public class Result {
    private boolean flag;
    private Integer code;
    private Integer count;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(boolean flag, Integer code, String msg) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }

    public Result(boolean flag, Integer code, String msg, Object data) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Result(boolean flag, Integer code, String msg, Object data, Integer count) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }
    public static Result ok(){
        Result result=new Result(true,StatusCode.OK,null);
        return result;
    }
    public static Result ok(String message){
        Result result=new Result(true,StatusCode.OK,message);
        return result;
    }
    public static Result ok(String message,Object data){
        Result result=new Result(true,StatusCode.OK,message,data);
        return result;
    }

    public static Result fail(){
        Result result=new Result(false,StatusCode.ERROR,null);
        return result;
    }
    public static Result fail(String message){
        Result result=new Result(false,StatusCode.ERROR,message);
        return result;
    }
    public static Result fail(String message,Object data){
        Result result=new Result(false,StatusCode.ERROR,message,data);
        return result;
    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
