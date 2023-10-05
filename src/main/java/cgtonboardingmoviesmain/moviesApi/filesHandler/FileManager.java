package cgtonboardingmoviesmain.moviesApi.filesHandler;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;
import java.util.Random;

@Component
public class FileManager {
    public String saveCode(Movie movie) {
        String imgName = movie.getMovieImageName();
        String encodedString = null;
        if (imgName.isBlank()) {
            //log error
        } else {
            try {
                File file = new File(imgName);
                FileInputStream fis = new FileInputStream(file);
                byte[] fileContent = new byte[(int) file.length()];
                fis.read(fileContent);
                encodedString = Base64.getEncoder().encodeToString(fileContent);

            } catch (FileNotFoundException e) {
                //throw
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "data:image/jpg;base64," + encodedString;
    }

    public String saveImageName(CreateMovieDto createMovieDto) {
        String dataUri = createMovieDto.getMovieImage();
        String imageName = null;

        if(dataUri.isBlank()){
            //log error
        }else {
            Random random = new Random();
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(dataUri);
                imageName = "movieImage_" + random.nextInt(100) + ".jpg";
                try {
                    FileOutputStream fos = new FileOutputStream(imageName);
                    fos.write(decodedBytes);
                } catch (FileNotFoundException e){
                   //throw
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IllegalArgumentException e) {
                //ilegal base64 to decode
                e.printStackTrace();
            }

        }
        return imageName;
    }

    public void deleteImage(Movie movie){
        String imgName = movie.getMovieImageName();
        if(imgName.isBlank()){
            //log error
        }else {
            try {
                File f = new File(imgName);
                if(f.delete()){
                    //log
                    //System.out.println("Deleted");
                }else {
                    //log
                    //System.out.println("Error");
                }
            }catch (NullPointerException e){
                //throw
            }
        }



    }
    public String updateImage(CreateMovieDto createMovieDto, Movie movie){
        if(createMovieDto != null && movie != null){
            byte[] fileContent = new byte[0];
            try {
                File f = new File(movie.getMovieImageName());
                fileContent = FileUtils.readFileToByteArray(f);
            } catch (IOException e) {
                //log
                throw new RuntimeException(e);
            }catch (NullPointerException e){
                //throw
            }
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            if(encodedString.equals(createMovieDto.getMovieImage())){
                //System.out.println("SAME NAME");
                //log
                return movie.getMovieImageName();
            }else {
                //System.out.println("DIFFERENT NAME");
                //log
                deleteImage(movie);
                return saveImageName(createMovieDto);
            }
        }else {
            //log error with input
        }
        return null;
    }


}
