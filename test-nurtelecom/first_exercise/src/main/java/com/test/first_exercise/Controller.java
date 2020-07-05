package com.test.first_exercise;

import com.google.gson.Gson;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.InputSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String getIndex(Model model) throws Exception {
        Gson gson = new Gson();
        //Weather
        String urlWeather = "http://dataservice.accuweather.com/currentconditions/v1/222844?apikey=F6XIi4HYIy2wfwMLAMddrNBqEUS6vXgd";
        String jsonWeather = getJson(urlWeather).replace("[","").replace("]","");
        Weather weather = gson.fromJson(jsonWeather, Weather.class);
        model.addAttribute("weather",weather);
        model.addAttribute("temp",weather.getTemperature().getMetric().getValue());

        //Valute
        String urlValute = "https://www.cbr-xml-daily.ru/daily_jsonp.js";
        String jsonValute = getJson(urlValute).replace("CBR_XML_Daily_Ru(","").replace(");","");
        Valutes valutes = gson.fromJson(jsonValute,Valutes.class);
        Float som = 100 / valutes.getValute().getKGS().getValue();
        model.addAttribute("dollar",som*valutes.getValute().getUSD().getValue());
        model.addAttribute("euro",som*valutes.getValute().getEUR().getValue());

        //News
        String urlNews = "https://lenta.ru/rss/news";
        String xmlNews = getJson(urlNews);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document document = dBuilder.parse(new InputSource(new StringReader(xmlNews)));
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        List<ResultItemModel> news = new ArrayList<>();
        for (int i=1; i<6 ; i++){
            news.add(new ResultItemModel(xpath.evaluate("/rss/channel/item["+i+"]/title",document),
                    xpath.evaluate("/rss/channel/item["+i+"]/description",document),
                    xpath.evaluate("/rss/channel/item["+i+"]/link",document)));
        }
        model.addAttribute("news",news);
        return "index";
    }

    private String getJson(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    @GetMapping("/observer")
    public String observe(){
        return "observer";
    }

    @GetMapping("/observer-result")
    public String getObserverResult(){
        return "observerResult";
    }

    @GetMapping("/daily-planner")
    public String getDailyPlanner(){
        return "dailyPlanner";
    }

    @GetMapping("/observer-search")
    public String observerSearch(@RequestParam("url") String url,
            @RequestParam("updateFrequency") String updateFrequency,
            @RequestParam("keyWords") String keyWords, RedirectAttributes attributes){
        attributes.addFlashAttribute("updateFrequency",updateFrequency);
        attributes.addFlashAttribute("keysW",keyWords);
        attributes.addFlashAttribute("userUrl",url);
        attributes.addFlashAttribute("url","http://localhost:8080/observer-search?keyWords="+keyWords+"&updateFrequency="+updateFrequency+"&url="+url);
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("topic_title");
            List<ResultItemModel> resultItemModels = new ArrayList<>();
            String[] arr = keyWords.split(", ");
            for (Element element:elements){
                for (String a:arr){
                    if(element.text().toLowerCase().indexOf(a.toLowerCase())>0) {
                        resultItemModels.add(new ResultItemModel(element.text(), element.attr("href")));
                        break;
                    }
                }
            }
            if(resultItemModels.size()>=0)
                attributes.addFlashAttribute("items",resultItemModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/observer-result";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchText") String searchText, RedirectAttributes attributes) {
        try {
            String searchWord = searchText.replaceAll(" ","+");

            //Google
            Document doc = Jsoup.connect("https://www.google.com/search?q="+searchWord).get();
            List<String> titles = doc.getElementsByClass("LC20lb DKV0Md").stream()
                    .map(Element::text)
                    .collect(Collectors.toList());
            Elements elements_url = doc.getElementsByClass("r");
            List<String> texts = doc.getElementsByClass("st").stream()
                    .map(Element::text)
                    .collect(Collectors.toList());
            List<ResultItemModel> resultItemModels = new ArrayList<>();
            if (titles.size()==0 || elements_url.size()==0 || texts.size()==0){
                System.out.println("Not found");
            } else {
                for(int i = 0; i < 5 && i<titles.size() && i<elements_url.size() && i<texts.size(); i++){
                    resultItemModels.add(new ResultItemModel(titles.get(i), texts.get(i), elements_url.get(i).select("a").first().attr("href")));
                }
                attributes.addFlashAttribute("listItem",resultItemModels);
            }

            //Yandex
            Document doc1 = Jsoup.connect("https://yandex.ru/search/?text="+searchWord).get();
            List<String> titles1 = doc1.getElementsByClass("organic__url-text").stream()
                    .map(Element::text)
                    .collect(Collectors.toList());
            Elements elements_url1 = doc1.getElementsByClass("organic__title-wrapper");
            List<String> texts1 = doc1.getElementsByClass("extended-text__short").stream()
                    .map(Element::text)
                    .collect(Collectors.toList());
            List<ResultItemModel> resultItemModels1 = new ArrayList<>();
            if (titles1.size()==0 || elements_url1.size()==0 || texts1.size()==0){
                System.out.println("Not found");
            } else {
                for(int i = 0; i < 5 && i<titles1.size() && i<elements_url1.size() && i<texts1.size(); i++){
                    resultItemModels1.add(new ResultItemModel(titles1.get(i), texts1.get(i), elements_url1.get(i).select("a").first().attr("href")));
                }
                attributes.addFlashAttribute("listItem1",resultItemModels1);
            }

            //Bing
            Document doc2 = Jsoup.connect("https://api.proxycrawl.com/?token=pLspGKDlxyeicInjdjJpSQ&url=https://www.bing.com/search?q="+searchWord).get();
            Elements elements = doc2.getElementsByClass("b_algo");
            List<ResultItemModel> resultItemModels2 = new ArrayList<>();
            if (elements.size() == 0){
                System.out.println("Not found");
            } else {
                for(int i = 0; i < 5 && i<elements.size(); i++){
                    resultItemModels2.add(new ResultItemModel(elements.get(i).select("h2").text(), elements.get(i).select("p").text(), elements.get(i).select("a").first().attr("href")));
                }
                attributes.addFlashAttribute("listItem2",resultItemModels2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
