package ca.gc.hc.nhpd.dto.test;

import ca.gc.hc.nhpd.dto.Constituent;

import java.util.Random;


public class ConstituentTester {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        System.out.println("ConstituentTester");
        
        // String testString = "<ap>beta-Carotene</ap><cn>beta-Carotene</cn><pn>(all-E)-1,1'-(3,7,12,16-Tetramethyl-1,3,5,7,9,11,13,15,17-octadecanonaene-1,18-diyl)bis[2,6,6-trimethylcyclohexene]<br/>all-trans-beta-Carotene<br/>beta,beta-Carotene</pn>~<ap>beta-Carotene</ap><cn>beta-Carotene</cn><pn>(all-E)-1,1'-(3,7,12,16-Tetramethyl-1,3,5,7,9,11,13,15,17-octadecanonaene-1,18-diyl)bis[2,6,6-trimethylcyclohexene]<br/>all-trans-beta-Carotene<br/>beta,beta-Carotene</pn>~<ap>Iron</ap><cn>Iron</cn><pn>Iron</pn>~<ap>Lycopene</ap><cn>Lycopene</cn><pn>(all-trans)-Lycopene<br/>psi,psi-Carotene</pn>~<ap>Zeaxanthin</ap><cn>Zeaxanthin</cn><pn>(3R,3'R)-Dihydroxy-beta-carotene<br/>all-trans-beta-Carotene-3,3'-diol</pn>~<ap>Citramalic acid</ap><cn>Citramalic acid</cn><pn>2-Hydroxy-2-methylbutanedioic acid<br/>2-Hydroxy-2-methylsuccinic acid<br/>2-Methylmalic acid<br/>alpha-Hydroxypyrotartaric acid</pn>~<ap>Nerol</ap><cn>(2E)-3,7-dimethylocta-2,6-dien-1-ol<br/>2,6-Octadien-1-ol, 3,7-dimethyl-, (2Z)-<br/>2,6-Octadien-1-ol, 3,7-dimethyl-, (Z)-</cn><pn>Nerol</pn>~<ap>Vitamin C</ap><cn>Ascorbic acid<br/>Vitamin C</cn><pn>Vitamin C</pn>~<ap>Vitamin C</ap><cn>Ascorbic acid<br/>Vitamin C</cn><pn>Vitamin C</pn>~<ap>Hesperidin</ap><cn>Hesperidin</cn><pn>(2S)-7-[[6-O-(6-Deoxy-alpha-L-mannopyranosyl)-beta-D-glucopyranosyl]oxy]-2,3-dihydro-5-hydroxy-2-(3-hydroxy-4-methoxyphenyl)-4H-1-benzopyran-4-one</pn>~<ap>Tangeretin</ap><cn>Tangeretin</cn><pn>5,6,7,8,4'-Pentamethoxyflavone<br/>5,6,7,8-Tetramethoxy-2-(4-methoxyphenyl)-4-benzopyrone</pn>~<ap>Bioflavonoids</ap><cn>Bioflavonoids</cn><pn>Bioflavonoids</pn>~<ap>Bioflavonoids</ap><cn>Bioflavonoids</cn><pn>Bioflavonoids</pn>~<ap>Citrus bioflavonoids</ap><cn>Citrus bioflavonoids</cn><pn>Citrus bioflavonoids</pn>";
        
        String testString = "<ap>beta-Carotene</ap><cn>beta-Carotene</cn><pn>(all-E)-1,1'-(3,7,12,16-Tetramethyl-1,3,5,7,9,11,13,15,17-octadecanonaene-1,18-diyl)bis[2,6,6-trimethylcyclohexene]<br/>all-trans-beta-Carotene<br/>beta,beta-Carotene</pn>";
        
        Constituent constituent = new Constituent(testString, testString);
        
        System.out.println(constituent.toString());
        
        // System.out.println(Constituent.extractText(testString, startTag, endTag));
        
        
    }

    public int dot = 100;
    public static int here = 0;
    
    public static void step() {
        
        Random rnd = new Random();
        
        here = here + rnd.nextInt();
        
    }
    
    
}
