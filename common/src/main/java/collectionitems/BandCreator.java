package collectionitems;

import input.EndOfInputException;
import input.InputSource;

/**
 * Class for creating new bands with fields provided by the user
 */
public class BandCreator {
    private InputSource inputSource;

    public BandCreator(InputSource inputSource){
        this.inputSource = inputSource;
    }

    public MusicBand createBand() throws EndOfInputException {
        MusicBand band = new MusicBand();
        setBandName(band);
        Coordinates coordinates = new Coordinates();
        setX(coordinates);
        setY(coordinates);
        band.setCoordinates(coordinates);
        setNumOfParticipants(band);
        setAlbumsCount(band);
        setDescription(band);
        setGenre(band);
        setBestAlbum(band);
        return band;
    }

    private void setBandName(MusicBand band) throws EndOfInputException {
        while (true){
            try{
                band.setName(inputSource.readString("Name"));
                break;
            }
            catch (WrongArgumentException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private void setX(Coordinates coordinates) throws EndOfInputException {
        while(true){
            try {
                Float x = Float.parseFloat(inputSource.readString("X coordinate"));
                coordinates.setX(x);
                break;
            }
            catch(WrongArgumentException ex){
                System.out.println(ex.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Coordinate must be a number");
            }
        }
    }

    private void setY(Coordinates coordinates) throws EndOfInputException {
        while(true){
            try {
                float y = Float.parseFloat(inputSource.readString("Y coordinate"));
                coordinates.setY(y);
                break;
            }
            catch(WrongArgumentException ex){
                System.out.println(ex.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Coordinate must be a number");
            }
        }
    }

    private void setNumOfParticipants(MusicBand band) throws EndOfInputException
    {
        while(true){
            try {
                int numOfParticipants = Integer.parseInt(inputSource.readString("Number of participants"));
                band.setNumberOfParticipants(numOfParticipants);
                break;
            }
            catch(WrongArgumentException ex){
                System.out.println(ex.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Number of participants must be a number");
            }
        }
    }

    private void setAlbumsCount(MusicBand band) throws EndOfInputException
    {
        while(true){
            try {
                long albumsCount = Long.parseLong(inputSource.readString("Albums count"));
                band.setAlbumsCount(albumsCount);
                break;
            }
            catch(WrongArgumentException ex){
                System.out.println(ex.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Albums count must be a number");
            }
        }
    }

    private void setDescription(MusicBand band) throws EndOfInputException
    {
        String description = inputSource.readString("Description, leave empty if null");
        if(!description.equals("")){
            band.setDescription(description);
        }
    }

    private void setGenre(MusicBand band) throws EndOfInputException
    {
        while(true){
            String genre = inputSource.readString("Genre, leave empty if null");
            switch (genre){
                case "soul": band.setGenre(MusicGenre.SOUL); break;
                case "blues": band.setGenre(MusicGenre.BLUES); break;
                case "punk rock": band.setGenre(MusicGenre.PUNK_ROCK); break;
                case "post punk": band.setGenre(MusicGenre.POST_PUNK); break;
                case "brit pop": band.setGenre(MusicGenre.BRIT_POP); break;
                case "": break;
                default: System.out.println("Incorrect genre"); continue;
            }
            break;
        }
    }

    private void setBestAlbum(MusicBand band) throws EndOfInputException
    {
        String addAlbum = inputSource.readString("Do you want to add best album? Print 1 if yes");
        if(addAlbum.equals("1")) {
            Album album = new Album();
            setAlbumName(album);
            setAlbumTrack(album);
            setAlbumLength(album);
            setAlbumSales(album);
            band.setBestAlbum(album);
        }
    }

    private void setAlbumName(Album album) throws EndOfInputException
    {
        while(true){
            String name = inputSource.readString("Album name");
            try {
                album.setName(name);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setAlbumTrack(Album album) throws EndOfInputException
    {
        while(true){
            try {
                long tracks = Long.parseLong(inputSource.readString("Album tracks"));
                album.setTracks(tracks);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Albums tracks must be a number");
            }
        }
    }
    private void setAlbumLength(Album album) throws EndOfInputException
    {
        while(true){
            try {
                Integer length = Integer.parseInt(inputSource.readString("Album length"));
                album.setLength(length);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Albums length must be a number");
            }
        }
    }

    private void setAlbumSales(Album album) throws EndOfInputException
    {
        while(true){
            try {
                Float sales = Float.parseFloat(inputSource.readString("Album sales"));
                album.setSales(sales);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException ex){
                System.out.println("Albums sales must be a number");
            }
        }
    }
}
