package cc.twittertools.optimization;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class ResultOutPut {
  public static void main(String []args) throws Exception{
    
    ArrayList<String> queryList = new ArrayList<String>();
    queryList.add("Ron Weasley birthday hermion harri potter hogwart weaslei jimmi fallon lavend wizard ginni");
    queryList.add("Merging of US Air and American airlin airwai dividend aadvantag mile flight elit allianc oneworld merger");
    queryList.add("muscle pain from statins lipitor cholesterol liver mayo doctor clinic medic atorvastatin side calcium");
    queryList.add("Hubble oldest star telescop uncertainti methuselah precis ag bright space parallax billion bond");
    queryList.add("commentary on naming storm Nemo weather blizzard snow wind insur meteorologist hurrican fridai boston alaska");
    queryList.add("book club members bonu arbitr purchas select doubledai redeem creditdebit literari fulfil cart");
    queryList.add("Boko Haram kidnapped French tourists nigeria yusuf maiduguri nigerian detent borno islam abroad jihad african");
    queryList.add("Tiger Woods regains title tournament palmer rank highestpaid golfer nicklauss pga bai arnold hasnt");
    queryList.add("care of Iditarod dogs musher veterinarian race dvm peta sled stuart nelson drop checkpoint");
    queryList.add("Sherlock Elementary BBC CBS repli holm watson juli cumberbatch benedict think moffat episod sai");
    queryList.add("Costa Concordia shipwreck coast cruis ship giglio disast italian aground diver tuscan schettino");
    queryList.add("Chinua Achebe death nigeria conrad igbo african novel coloni literatur biafra writer feminin");
    queryList.add("Evernote hacked zdnet password asdfasdf reset encrypt quotquotgt user forum antiviru notetak");
    queryList.add("Election of Hugo Chavez successor maduro capril venezuela victori embalm cnn leader legaci mdash presidenti");
    queryList.add("National Zoo Panda, insemination mei cub tian giant xiang bao den bamboo wild keeper  ");
    queryList.add("Dorner truck compensation jona kpcc lapd cabin bernardino carranza manhunt quan attornei wound");
    queryList.add("Pope washed Muslims feet franci vatican ncr cathol priest church edt rite holi morlino");
    queryList.add("Bombing police headquarters Kirkuk isi suicid attack iraq firefight mosul turkmen baghdad kurdish deton");
    queryList.add("injuries by pets webmd dog cat acl petmd breed alzheim symptom ligament ruptur ");
    queryList.add("Organized crime sports doping Australia asada antidop substanc gmt hkt rugbi acc afl bet athlet");
    queryList.add("Irish laundries apology magdalen browser halappanavar alevel workhous savita ireland abort kitten monkei");
    queryList.add("whooping cough epidemic pertussi vaccin infant cdph webmd cdc immun tdap chapman diseas");
    queryList.add("Bulgarian protesters self immolate selfimmol palach pragu czech bulgaria sofia psychologist immol spiegel huffpost");
    queryList.add("cherry blossom Washington dcbloom bloom tree mar via yoshino april peak basin potomac");
    queryList.add("Argo wins Oscar affleck ben actor heslov arkin globe chri motion daylewi macfarlan");
    queryList.add("US fines Google over Street View epic ftc saskatoon radi czech breach bookstor defcon pci america");
    queryList.add("Mad Men season 6 don weiner episod pete peggi megan ted salli betti scdp");
    queryList.add("Hostess bought by Apollo twinki dow morningstar indexessm scripp mercantil jone proprietari shelf huffpost");
    queryList.add("Ed Koch death mayor jewish cuomo isbn dinkin york est hall citi democrat");
    queryList.add("UK passes marriage bill samesex legisl scottish religi sex coupl civil church bishop partnership");
    queryList.add("Higgs Boson discovery particl gaug vacuum quark symmetri model mass field scalar theori");
    queryList.add("Boko Haram Amnesty opposition buhari sect nigeria dialogu compens nigerian borno mediat feder govern");
    queryList.add("Eastern Australia Floods queensland cyclon brisban oswald bundaberg dam januari tropic rockhampton gladston");
    queryList.add("Sotomayor prosecutor racial comments hill calhoun contributor sonia racist huffpost healthcar youv luke morri");
    queryList.add("Port Said football riot death sentences verdict cairo egypt masri mubarak edt rabaa ultra hosni fan");
    queryList.add("yarn bombing knit mateo lorna watt san jill graffiti knitter houston downtown");
    queryList.add("David Cameron apology Amritsar massacr jallianwala bagh punjab templ sikh broadscatt golden load british");
    queryList.add("Olympics drops wrestling ioc pentathlon foundat vote samaranch softbal grecoroman uipm freestyl core");
    queryList.add("Chelyabinsk meteor damage bibcod doi asteroid documentari russia marco geophys fuent airburst orbit");
    queryList.add("arrest of Craig Wilson for drive-by shooting in DC newschannel abc livestream listen latifah assault click mdash bio immigr");
    queryList.add("Downton Abbey Lady Mary beau repin crawlei repli pin edith julian matthew branson sybil dockeri");
    queryList.add("Kate Middleton maternity wear bump newser cafemom stylewatch mom pregnanc princess circumcis exwif deepen");
    queryList.add("US Embassy in Ankara bombed turkish turkei deton entranc explos ambassador guard blast photoburhan suicid");
    queryList.add("math Common Core ccssm calculu algebra coalit assess museum geometri cryptographi differenti cbm");
    queryList.add("snow blower problems snowblow disassembl sixtyfiveford repair tecumseh craftsman doublewid carburetor shccommonq carb");
    queryList.add("Type II diabetes research insulin clinic alabama mellitu birmingham patient cell glucos type phase");
    queryList.add("Pope candidates conclav bergoglio cathol vatican church scola bishop papal archbishop ouellet");
    queryList.add("Sinkhole rescues descript omaha scooter husker worldherald full nebraska creighton hansen lauri");
    queryList.add("Russian meteorite conspiracy meteor chelyabinsk zhirinovski intercept theori lavrov infowar motherboard missil kerri");
    queryList.add("Shahbag protest jamaat bangladesh awami jamaateislami bangladeshi opensecur tribun demand movement seat");
    queryList.add("HIV baby cured jul natur reservoir aug mississippi viru robochemist uit rebound methan ");
    queryList.add("Oz The Great and Powerful opens disnei weekend gross adjust film kuni franco fantasi nomin movi");
    queryList.add("dog off leash frick park tenni schenlei environment train listen picnic riverview rink");
    queryList.add("dark pool trading investor price stock brokerdeal exchang investopedia hft market transact barclai");
    queryList.add("Barbara Walters chicken pox widow terminolog parkinson battl deck shibboleth poxbarbara reallyit toppler onphoto");
//    String query = "Ron Weasley birthday hermion harri ron potter hogwart weaslei jimmi fallon avend wizard";
    for(int i =24; i<25;i++){
          FileWriter fos = new FileWriter("output4FinalMB"+(171+i)+".txt");
          BufferedWriter bw=new BufferedWriter(fos);
          String indexPath = "ExpendedResult4MB"+(171+i);
          String query = queryList.get(i);
          System.out.println(indexPath+query);
          Scoring ft = new Scoring(indexPath);
          int collection_length1 = ft.collectionLength("TweetContent",indexPath);
          int collection_length2 = ft.collectionLength("PageContent",indexPath);
          int docNum = ft.getNumDocs();
          double avg1 = collection_length1/docNum;
          System.out.println("tweetContent average = "+avg1);
          double avg2 = collection_length2/docNum;
          System.out.println("PageContent average = "+avg2);
          TreeMap<Integer,Double> result = ft.CombinedScore(query, avg1,avg2,indexPath);    
          Set<Integer> sets = result.keySet();
          int flag = 0;
          for(int set : sets){    
            if(flag<1000){
              String twitterID = ft.getTweetID(set);
              String queryID = ft.getQueryID(set);
              String str = queryID + " Q0 " + twitterID + " " + (flag+1) + " " + result.values().toArray()[flag] + " Upitt";      
              bw.write(str);
              bw.newLine();
              flag++;
              
            }
            else
            break;
            
          }
          bw.close();
          fos.close();
    }


  }
}
