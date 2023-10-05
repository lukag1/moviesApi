package cgtonboardingmoviesmain.moviesApi.filesHandler;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;
import java.util.Random;

@Component
public class FileManager {

    LoggerImpl logger;

    public FileManager(LoggerImpl logger) {
        this.logger = logger;
    }

    public String saveCode(Movie movie, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started encoding image to code in File Manager");
        String imgName = movie.getMovieImageName();
        String encodedString = null;
        if (imgName.isBlank()) {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Image name is blank");
        } else {
            try {
                File file = new File(imgName);
                FileInputStream fis = new FileInputStream(file);
                byte[] fileContent = new byte[(int) file.length()];
                fis.read(fileContent);
                encodedString = Base64.getEncoder().encodeToString(fileContent);
                logger.formatLogMessageGen(LogLevel.INFO,lm,"Succesfull encoding in File Manager");

            } catch (FileNotFoundException e) {
                logger.formatLogMessageException(LogLevel.ERROR,lm, "FileNotFoundException","File not found, check image name");
                //throw new FileNotFoundException("");
            } catch (IOException e) {
                logger.formatLogMessageException(LogLevel.ERROR,lm, "IOException","IO error");
                throw new RuntimeException(e);
            }
        }
        return "data:image/jpg;base64," + encodedString;
    }

    public String saveImageName(CreateMovieDto createMovieDto, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started decoding code to image in File Manager");
        String dataUri = createMovieDto.getMovieImage();
        String imageName = null;

        if(dataUri.isBlank()){
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Base64 code is blank, cant decode");
        }else {
            Random random = new Random();
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(dataUri);
                imageName = "movieImage_" + random.nextInt(100) + ".jpg";
                try {
                    FileOutputStream fos = new FileOutputStream(imageName);
                    fos.write(decodedBytes);
                    logger.formatLogMessageGen(LogLevel.INFO,lm,"Saved movie image succesfull");
                } catch (FileNotFoundException e){
                    logger.formatLogMessageException(LogLevel.ERROR,lm, "FileNotFoundException","File not found, check image name");
                    //throw new FileNotFoundException("");
                } catch (IOException e) {
                    logger.formatLogMessageException(LogLevel.ERROR,lm, "IOException","IO error");
                    //throw new IOException("");
                }
            } catch (IllegalArgumentException e) {
                logger.formatLogMessageException(LogLevel.ERROR,lm, "IllegalArgumentException"," IllegalArgumentException");
                //throw new IllegalAccessException("Error with base 64 code");
            }
        }
        return imageName;
    }

    public void deleteImage(Movie movie, LoggerModel lm){
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started deleting image in File Manager");
        String imgName = movie.getMovieImageName();
        if(imgName.isBlank()){
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Image name is blank, cant encode");
        }else {
            try {
                File f = new File(imgName);
                if(f.delete()){
                    logger.formatErrorLogMessageError(LogLevel.INFO, lm, "Movie image is deleted");
                }else {
                    logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Error with deleting image");
                }
            }catch (NullPointerException e){
                logger.formatLogMessageException(LogLevel.ERROR, lm,"NullPointerException", "Cant find image file");
                throw new NullPointerException("Cant find image file");
            }
        }

    }
    public String updateImage(CreateMovieDto createMovieDto, Movie movie, LoggerModel lm){
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started updating image in File Manager");
        String imageName = null;
        if(createMovieDto != null && movie != null){
            byte[] fileContent = new byte[0];
            try {
                File f = new File(movie.getMovieImageName());
                fileContent = FileUtils.readFileToByteArray(f);
            } catch (IOException e) {
                logger.formatLogMessageException(LogLevel.ERROR,lm, "IOException","IO error");
                //throw new IOException("");
            }catch (NullPointerException e){
                logger.formatLogMessageException(LogLevel.ERROR, lm,"NullPointerException", "Cant find image file");
                throw new NullPointerException("Cant find image file");
            }
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            if(encodedString.equals(createMovieDto.getMovieImage())){
                logger.formatLogMessageGen(LogLevel.INFO, lm, "Same image");
                imageName = movie.getMovieImageName();
            }else {
                logger.formatLogMessageGen(LogLevel.INFO, lm, "Different image, needs to change");
                deleteImage(movie,lm);
                imageName = saveImageName(createMovieDto,lm);
            }
        }else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Error with updating image, input is null");
        }
        return imageName;
    }


}
