package cgtonboardingmoviesmain.moviesApi.logger;

public class LoggerModel {

    private String logLevel;
    private String className;
    private String methodName;
    private String dateTime;
    private String requestId;
    private String exceptionName;
    private String statusCode;
    private Object logMessage;
    private Object stackTrace;

    public LoggerModel() {
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Object getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(Object logMessage) {
        this.logMessage = logMessage;
    }

    public Object getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(Object stackTrace) {
        this.stackTrace = stackTrace;
    }
}
