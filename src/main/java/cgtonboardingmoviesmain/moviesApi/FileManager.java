package cgtonboardingmoviesmain.moviesApi;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Component
public class FileManager {
    public String saveCode(Movie movie){
        File file = new File(movie.getMovieImageName());

        try (FileInputStream fis = new FileInputStream(file)){
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            return "data:image/jpg;base64,"+encodedString;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveImageName(CreateMovieDto createMovieDto) {
        String dataUri = createMovieDto.getMovieImage();
        Random random = new Random();
        String imageName = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(dataUri);
            imageName = "movieImage_" + random.nextInt(100) + ".jpg";
            try (FileOutputStream fos = new FileOutputStream(imageName)) {
                fos.write(decodedBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return imageName;
    }

    // ADD DELETE AND UPDATE
    public void deleteImage(Movie movie){
        String imgName = movie.getMovieImageName();
        File f = new File(imgName);

        if(f.delete()){
            System.out.println("Deleted");
        }else {
            System.out.println("Error");
        }
    }
    public String updateImage(CreateMovieDto createMovieDto, Movie movie){
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(new File(movie.getMovieImageName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        if(encodedString.equals(createMovieDto.getMovieImage())){
        System.out.println("SAME NAME");
            return movie.getMovieImageName();
        }else {
            System.out.println("DIFFERENT NAME");
            deleteImage(movie);
            return saveImageName(createMovieDto);
        }
    }


}
