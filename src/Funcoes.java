/*
 * 
 * Created on : 28-jul-17
 * Author     : Levi Monteiro Martins 
 *
 *-----------------------------------------------------------------------------
 * Revision History (Release 1.0.0.0)
 * The following code was created based on the [Van Gent, P. (2016).
 * Analyzing a Discrete Heart Rate Signal Using Python] tutorial.
 * The source code of Van Gent, P. can be found on: 
 * https://github.com/paulvangentcom/heartrate_analysis_python
 *
 * Note: The implementation of low pass Butterworth filter used was designed 
 * by Subin E.K, Renuka. S, Krishna Chaitanya. Founded on the link:
 * http://ijcsit.com/docs/Volume%207/vol7issue5/ijcsit20160705029.pdf
 *
 *
 * Further code and validation review is needed.
 *-----------------------------------------------------------------------------
 * VERSION     AUTHOR/      DESCRIPTION OF CHANGE
 * OLD/NEW     DATE                RFC NO
 *-----------------------------------------------------------------------------
 * --/1.0  | Levi M. Martins  | Initial Create.
 *         | 28-Jul-2017      |
 *---------|------------------|---------------------------------------------------
 */
package funcoes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Funcoes {

    private String FILENAME = "/home/levimmartins/NetBeansProjects/Funcoes/src/dadosSENSOR2.txt";
    private static List<String> SensorValuesList = null;
    private static List<Float> savedAverages = null;
    private static List<Integer> peakList = null;
    private static List<Float> ybeat = null;
    private static List<Double> RR_list = null;
    private static List<Double> bpm = null;

    public static void main(String[] args) {

        Funcoes fn = new Funcoes();
        getData(fn.FILENAME);

        //Taking a subset for analysis 
        //A different approach in the selection of the range must be studied
        //Suggestion: use of a range of time.
        SensorValuesList = new ArrayList<String>(SensorValuesList.subList(900, 3500));
        rolling_mean(SensorValuesList, 0.75, 99.00);
        detect_peaks(SensorValuesList);
        calc_rr(99.00);
        calc_bpm();

    }

    public static void getData(String filename) {
        String csvFile = filename;
        String line = "";
        String cvsSplitBy = ",";

        String[] heartRateLine = null;
        StringBuilder sb = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                heartRateLine = line.split(cvsSplitBy);

                sb.append(heartRateLine[1]);
                sb.append("\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        SensorValuesList = new ArrayList<String>(Arrays.asList((sb.toString().split("\n"))));
        SensorValuesList.remove(0); //Remove  the header 'hart' (first line of the file)

    }

    public static void process(List dataset, double hrw, double fs) {
        //rollmean

    }

    public static void rolling_mean(List<String> dataset, double hrw, double fs) {

        Integer window = (int) Math.round(hrw * fs); //range used to calculate the average mean. Variable used in for loop
        Integer windowForAverage = (int) Math.round(hrw * fs); //variable used to calculate the average	

        savedAverages = new ArrayList<Float>();

        float average;

        float sum;
        int i = 0, n = 0;

        float size = 0;

        while (n <= (dataset.size() - windowForAverage)) {
            sum = 0;
            average = 0;

            for (i = n; i < window; i++) {
                sum = sum + Float.parseFloat((String) dataset.get(i));
            }

            n++;
            i = n;
            window++;
            size += windowForAverage;

            average = sum / windowForAverage;
            savedAverages.add(average);

        }

        //Setting the SensorValuesList.size() to the same size of savedAverages.size()
        for (int j = SensorValuesList.size() - 1; j >= savedAverages.size(); j--) {
            SensorValuesList.remove(j);
        }

    }

    public static double mean(List<Double> values) {
        double sum = 0;

        for (int i = 0; i < values.size(); i++) {
            sum = sum + values.get(i);
        }

        return (sum / values.size());

    }

    public static void detect_peaks(List<String> dataset) {
        List<Float> window = new ArrayList<Float>();
        peakList = new ArrayList<Integer>();
        ybeat = new ArrayList<Float>();
        Integer listpos = 0;
        float rollingmean;

        for (int i = 0; i < dataset.size(); i++) {
            rollingmean = savedAverages.get(listpos);

            if ((Float.parseFloat((String) dataset.get(i)) <= rollingmean) && (window.size() <= 1)) {
                listpos++;
            } else if (Float.parseFloat((String) dataset.get(i)) > rollingmean) {
                window.add(Float.parseFloat((String) dataset.get(i)));
                listpos++;
            } else {
                Integer beatPosition;

                beatPosition = (listpos - window.size()) + (window.indexOf(Collections.max(window)));
                peakList.add(beatPosition);
                window = new ArrayList<Float>();
                listpos++;
            }
        }

        for (int i = 0; i < peakList.size(); i++) {
            ybeat.add(Float.parseFloat((String) dataset.get(peakList.get(i))));
        }

        return;
    }

    public static void calc_rr(double fs) {
        RR_list = new ArrayList<Double>();
        List<Integer> peaks = new ArrayList<Integer>();
        peaks = peakList;
        Integer cnt = 0;
        double RR_interval, ms_dist;

        while (cnt < peaks.size() - 1) {
            RR_interval = (peaks.get(cnt + 1) - peaks.get(cnt));
            ms_dist = ((RR_interval / fs) * 1000);
            RR_list.add(ms_dist);
            cnt++;
        }

        return;
    }

    public static void calc_bpm() {
        List<Double> RR_tempList = new ArrayList<Double>();
        bpm = new ArrayList<Double>();
        RR_tempList = RR_list;

        // 1 minuto = 60segundos = 60000 milisegundos
        bpm.add((60000 / (mean(RR_tempList))));

        System.out.println("\n\nMean: \t \t" + (mean(RR_tempList)));
        System.out.println("RR_tempList: \t" + RR_tempList);
        System.out.println("BPM: \t" + bpm);

    }

    //TODO
    public static void butter_lowpass_filter() {

    }
}
