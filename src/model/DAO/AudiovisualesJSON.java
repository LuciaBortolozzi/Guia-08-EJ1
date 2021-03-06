package model.DAO;

import model.*;

import java.io.*;
import java.util.*;
import javax.json.*;

public class AudiovisualesJSON {

    private static final String directorio = "D:\\\\IdeaProjects\\\\Guia-08\\\\src\\\\resources\\\\";
//    private static final String directorio = "C:\\\\Users\\\\Flor\\\\git\\\\Guia-08\\\\src\\\\resources\\";

    public static ArrayList<Audiovisuales> bajarAudiovisualesJSON(ArrayList<Audiovisuales> audiovisualesTXT, TreeSet<Actores> actoresTXT, ArrayList<Generos> generos) {

        Calendar fechaActual = Calendar.getInstance();
        File aJson;
        FileInputStream fsInJson;
        try {
            aJson = new File(directorio + "Audiovisuales.json");
            if (aJson.exists()) {
                fsInJson = new FileInputStream(aJson);
                JsonReader rdrJson = Json.createReader(fsInJson);
                JsonObject objJson = (JsonObject) rdrJson.readObject();
                JsonArray peliculas = (JsonArray) objJson.get("Peliculas");

                Iterator iteratorPeliculas = peliculas.iterator();
                while (iteratorPeliculas.hasNext()) {
                    JsonObject pelicula = (JsonObject) iteratorPeliculas.next();

                    boolean encontrado = false;

                    for (int i = 0; i < audiovisualesTXT.size(); i++) {
                        if (audiovisualesTXT.get(i) instanceof Peliculas) {
                            //MODIFICACION
                            if ((pelicula.get("nombre").toString()).replace(" ", "").replace("\"", "").toUpperCase().equals(audiovisualesTXT.get(i).getNombre().replace(" ", "").toUpperCase())) {
                                audiovisualesTXT.get(i).setSinopsis((pelicula.get("sinopsis").toString().replace("\"", "")));
                                audiovisualesTXT.get(i).setFechaPubli(fechaActual);

                                JsonArray actoresJSONArray = pelicula.getJsonArray("actores");
                                TreeSet<Actores> actoresArray = new TreeSet<Actores>();
                                agregarActorJSON(actoresTXT, actoresArray, actoresJSONArray);
                                audiovisualesTXT.get(i).setActores(actoresArray);

                                agregarGenero(audiovisualesTXT, generos, pelicula, i);

                                ((Peliculas) audiovisualesTXT.get(i)).setAnioFilm(Integer.parseInt((pelicula.get("anio")).toString()));
                                ((Peliculas) audiovisualesTXT.get(i)).setDuracion(Integer.parseInt(pelicula.get("duracion").toString()));
                                encontrado = true;
                            }
                        }
                    }

                    if (!encontrado) {
                        //NUEVA PELICULA

                        audiovisualesTXT.add(new Peliculas());
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setNombre((pelicula.get("nombre").toString().replace("\"", "").toUpperCase()));
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setCodigo(audiovisualesTXT.size());
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setSinopsis((pelicula.get("sinopsis").toString().replace("\"", "")));
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setFechaPubli(fechaActual);

                        JsonArray actoresJSONArray = pelicula.getJsonArray("actores");
                        TreeSet<Actores> actoresArray = new TreeSet<Actores>();
                        agregarActorJSON(actoresTXT, actoresArray, actoresJSONArray);
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setActores(actoresArray);

                        agregarGenero(audiovisualesTXT, generos, pelicula, audiovisualesTXT.size() - 1);

                        ((Peliculas) audiovisualesTXT.get(audiovisualesTXT.size() - 1)).setAnioFilm(Integer.parseInt((pelicula.get("anio")).toString()));
                        ((Peliculas) audiovisualesTXT.get(audiovisualesTXT.size() - 1)).setDuracion(Integer.parseInt(pelicula.get("duracion").toString()));
                    }
                }

                JsonArray series = (JsonArray) objJson.get("Series");
                Iterator iteratorSeries = series.iterator();
                while (iteratorSeries.hasNext()) {

                    JsonObject serie = (JsonObject) iteratorSeries.next();

                    boolean encontrado = false;

                    for (int i = 0; i < audiovisualesTXT.size(); i++) {
                        //MODIFICACION
                        if (audiovisualesTXT.get(i) instanceof Series) {
                            if ((serie.get("nombre").toString()).replace(" ", "").replace("\"", "").toUpperCase().equals(audiovisualesTXT.get(i).getNombre().replace(" ", "").toUpperCase()) &&
                                    Integer.parseInt((serie.get("temporada")).toString()) == ((Series) audiovisualesTXT.get(i)).getTemporada() &&
                                    Integer.parseInt((serie.get("episodio")).toString()) == ((Series) audiovisualesTXT.get(i)).getEpisodio()) {
                                audiovisualesTXT.get(i).setSinopsis((serie.get("sinopsis").toString()));
                                audiovisualesTXT.get(i).setFechaPubli(fechaActual);

                                JsonArray actoresJSONArray = serie.getJsonArray("actores");
                                TreeSet<Actores> actoresArray = new TreeSet<Actores>();
                                agregarActorJSON(actoresTXT, actoresArray, actoresJSONArray);
                                audiovisualesTXT.get(i).setActores(actoresArray);

                                agregarGenero(audiovisualesTXT, generos, serie, i);

                                ((Series) audiovisualesTXT.get(i)).setTemporada(Integer.parseInt((serie.get("temporada")).toString()));
                                ((Series) audiovisualesTXT.get(i)).setEpisodio(Integer.parseInt((serie.get("episodio")).toString()));
                                encontrado = true;
                            }
                        }
                    }

                    if (!encontrado) {
                        //NUEVA SERIE

                        audiovisualesTXT.add(new Series());
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setNombre((serie.get("nombre").toString().replace("\"", "").toUpperCase()));
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setCodigo(audiovisualesTXT.size());
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setSinopsis((serie.get("sinopsis").toString().replace("\"", "")));
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setFechaPubli(fechaActual);

                        JsonArray actoresJSONArray = serie.getJsonArray("actores");
                        TreeSet<Actores> actoresArray = new TreeSet<Actores>();
                        agregarActorJSON(actoresTXT, actoresArray, actoresJSONArray);
                        audiovisualesTXT.get(audiovisualesTXT.size() - 1).setActores(actoresArray);

                        agregarGenero(audiovisualesTXT, generos, serie, audiovisualesTXT.size() - 1);

                        ((Series) audiovisualesTXT.get(audiovisualesTXT.size()  - 1)).setTemporada(Integer.parseInt((serie.get("temporada")).toString()));
                        ((Series) audiovisualesTXT.get(audiovisualesTXT.size()  - 1)).setEpisodio(Integer.parseInt((serie.get("episodio")).toString()));
                    }
                }

                rdrJson.close();
                fsInJson.close();
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return audiovisualesTXT;
    }

    private static void agregarGenero(ArrayList<Audiovisuales> audiovisualesTXT, ArrayList<Generos> generos, JsonObject pelicula, int i) {
        Generos genero;
        Iterator<Generos> gen = generos.iterator();
        while (gen.hasNext()) {
            genero = gen.next();
           
            if (genero.getDescripcion().equals(pelicula.get("genero").toString().toUpperCase().trim().replace("\"", ""))) {

                audiovisualesTXT.get(i).setGenero(genero);
            }
        }
    }

    private static void agregarActorJSON(TreeSet<Actores> actoresTXT, TreeSet<Actores> actoresArray, JsonArray actoresJSONArray) {

        ArrayList<String> actoresST = new ArrayList<String>(actoresJSONArray.size());

        for (JsonValue jsonV : actoresJSONArray) {
            actoresST.add(jsonV.toString().replace("\"", "").trim());
        }

        for (String a : actoresST) {
            Actores actor;
            Iterator<Actores> actorIterator = actoresTXT.iterator();
            while (actorIterator.hasNext()) {
                actor = actorIterator.next();
                String concatenaNombreApellido = actor.getNombre().toUpperCase() + actor.getApellido().toUpperCase();
                if (concatenaNombreApellido.replace(" ", "").equals(a.replace("\"", "").replace(" ", "").toUpperCase())) {
                    actoresArray.add(actor);
                }
            }
        }
    }

    public static void grabarRecomendacionesSeriesJovenesJSON(Audiovisuales audiovisuales, int cantEpisodios) {

        try {

            File aJson = new File(directorio + "RecomendacionesSeriesJovenes.json");
            FileOutputStream fsOutJson = new FileOutputStream(aJson);
            JsonWriter wrtJson = Json.createWriter(fsOutJson);

            JsonObjectBuilder empresa = Json.createObjectBuilder();

            empresa.add("Empresa", "PeliSeri");

            JsonObjectBuilder detalleSerie = Json.createObjectBuilder();

            detalleSerie.add("Nombre", audiovisuales.getNombre().toUpperCase());

            detalleSerie.add("Genero", audiovisuales.getGenero().getDescripcion());

            JsonArrayBuilder actores = Json.createArrayBuilder();
            Actores a;
            Iterator<Actores> act = audiovisuales.getActores().iterator();
            while (act.hasNext()) {
                a = act.next();
                actores.add(a.getNombre() + " " + a.getApellido());
            }

            JsonArray arrayActores = actores.build();

            detalleSerie.add("Actores", arrayActores);

            detalleSerie.add("Sinopsis", audiovisuales.getSinopsis());

            detalleSerie.add("Temporada", ((Series) audiovisuales).getTemporada());

            detalleSerie.add("Total de Episodios", cantEpisodios);

            int sumaEstrellas = 0;
            int cantidadCalificaciones = 0;
            for (Calificaciones calificacion : audiovisuales.getCalificaciones()) {
                sumaEstrellas += calificacion.getEstrellas();
                cantidadCalificaciones++;
            }

            int calificacion = sumaEstrellas / cantidadCalificaciones;
            detalleSerie.add("Calificacion", calificacion);

            JsonObject JSerie = detalleSerie.build();

            empresa.add("Serie", JSerie);

            JsonObject JsonSerie = empresa.build();

            wrtJson.writeObject(JsonSerie);

            wrtJson.close();

            fsOutJson.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void grabarRecomendacionesPeliculasMayoresJSON(ArrayList<Audiovisuales> audiovisuales) {

        try {

            File aJson = new File(directorio + "RecomendacionesPeliculasMayores.json");
            FileOutputStream fsOutJson = new FileOutputStream(aJson);
            JsonWriter wrtJson = Json.createWriter(fsOutJson);

            JsonObjectBuilder empresa = Json.createObjectBuilder();

            empresa.add("Empresa", "PeliSeri");

            JsonArrayBuilder peliculas = Json.createArrayBuilder();

            for (Audiovisuales audi : audiovisuales) {

                JsonObjectBuilder detallePelicula = Json.createObjectBuilder();

                detallePelicula.add("Nombre", audi.getNombre().toUpperCase());

                detallePelicula.add("Genero", audi.getGenero().getDescripcion());

                JsonArrayBuilder actores = Json.createArrayBuilder();
                Actores a;
                Iterator<Actores> act = audi.getActores().iterator();
                while (act.hasNext()) {
                    a = act.next();
                    actores.add(a.getNombre() + " " + a.getApellido());
                }
                JsonArray arrayActores = actores.build();

                detallePelicula.add("Actores", arrayActores);

                detallePelicula.add("Sinopsis", audi.getSinopsis());

                detallePelicula.add("Anio", ((Peliculas) audi).getAnioFilm());

                detallePelicula.add("Duracion", ((Peliculas) audi).getDuracion());

                int sumaEstrellas = 0;
                int cantidadCalificaciones = 0;
                for (Calificaciones calificacion : audi.getCalificaciones()) {
                    sumaEstrellas += calificacion.getEstrellas();
                    cantidadCalificaciones++;
                }

                int calificacion = sumaEstrellas / cantidadCalificaciones;
                detallePelicula.add("Calificacion", calificacion);

                JsonObject JPelicula = detallePelicula.build();

                peliculas.add(JPelicula);
            }

            JsonArray JsonPeliculas = peliculas.build();

            empresa.add("Peliculas", JsonPeliculas);

            JsonObject JsonFinal = empresa.build();

            wrtJson.writeObject(JsonFinal);

            wrtJson.close();

            fsOutJson.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
