package cgtonboardingmoviesmain.moviesApi.logger;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoggerImpl {

    private String className(){
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }
    private String methodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    public LoggerModel generateLog(String logLevel, String message){
        LoggerModel lm = new LoggerModel();

        lm.setClassName(className());
        lm.setMethodName(methodName());
        lm.setDateTime(ZonedDateTime.now(ZoneId.of("UTC").normalized()).truncatedTo(ChronoUnit.SECONDS).toString());
        lm.setLogMessage(message);
        lm.setLogLevel(logLevel);

        return lm;
    }

    public LoggerModel generateLM(int xRequestId){
        LoggerModel lm = new LoggerModel();
        lm.setRequestId(String.valueOf(xRequestId));

        return lm;
    }
    public void printLogMess(String logLevel, String message){
        LoggerModel lm = new LoggerModel();
        lm.setLogLevel(logLevel);
        lm.setLogMessage(message);
        logMessage(lm);
    }

    public void formatLogMessageGen(String logLevel, LoggerModel lmIn, String message) {
        LoggerModel lm = new LoggerModel();
        lm.setLogLevel(logLevel);

        lm.setDateTime(ZonedDateTime.now(ZoneId.of("UTC").normalized()).truncatedTo(ChronoUnit.SECONDS).toString());
        lm.setRequestId(lmIn.getRequestId());
        lm.setClassName(className());
        lm.setMethodName(methodName());
        lm.setLogMessage(message);

        logMessage(lm);
    }

    public void formatLogMessageJSON(String logLevel, LoggerModel lmIn, Object message) {
        LoggerModel lm = new LoggerModel();
        lm.setLogLevel(logLevel);

        lm.setDateTime(ZonedDateTime.now(ZoneId.of("UTC").normalized()).truncatedTo(ChronoUnit.SECONDS).toString());
        lm.setRequestId(lmIn.getRequestId());
        lm.setClassName(className());
        lm.setMethodName(methodName());
        lm.setLogMessage(message);

        logMessage(lm);
    }

    public void formatLogMessageException (String logLevel, LoggerModel lmIn, String exceptionName, String message) {
        LoggerModel lm = new LoggerModel();
        lm.setLogLevel(logLevel);

        lm.setDateTime(ZonedDateTime.now(ZoneId.of("UTC").normalized()).truncatedTo(ChronoUnit.SECONDS).toString());
        lm.setRequestId(lmIn.getRequestId());
        lm.setClassName(className());
        lm.setMethodName(methodName());
        lm.setExceptionName(exceptionName);
        lm.setLogMessage(message);

        logMessage(lm);
    }

    public void formatErrorLogMessageError (String logLevel, LoggerModel lmIn, String message) {
        LoggerModel lm = new LoggerModel();
        lm.setLogLevel(logLevel);

        lm.setDateTime(ZonedDateTime.now(ZoneId.of("UTC").normalized()).truncatedTo(ChronoUnit.SECONDS).toString());
        lm.setRequestId(lmIn.getRequestId());
        lm.setClassName(className());
        lm.setMethodName(methodName());
        lm.setLogMessage(message);

        logMessage(lm);
    }

    public void formatLogMessageJSON(String logLevel, LoggerModel lmIn, String messageType, Object message) {
        LoggerModel lm = new LoggerModel();
        lm.setLogLevel(logLevel);

        lm.setDateTime(ZonedDateTime.now(ZoneId.of("UTC").normalized()).truncatedTo(ChronoUnit.SECONDS).toString());
        lm.setRequestId(lmIn.getRequestId());
        lm.setClassName(className());
        lm.setMethodName(methodName());
        lm.setLogMessage(message);

        logMessage(lm);
    }

    public void logMessage(LoggerModel lm) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String respForLog = gson.toJson(lm);
            String finalMessage = String.format(respForLog);

            System.out.println(finalMessage);

        } catch (Exception e){
//			e.printStackTrace();
            String errorMessage = "{\"logLevel\":\""+LogLevel.ERROR+"\",\"requestId\":\""+lm.getRequestId()+"\",\"logMessage\": \"Error occured parsing LM object - "+e.getMessage()+"\"}";
            System.out.println(errorMessage);
        }
    }


}
