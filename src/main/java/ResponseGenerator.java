import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by steph on 15/11/2017.
 */


public class ResponseGenerator {


    List <String> keyWords;
    private int selectedRestaurant=0 ;
    int msgCode ;
    ServiceCaller sc;

    public static final int LIST_RESTAURANT = 1,SHOW_HISTORY = 2,CHOOSE_PAYMENT = 3, LIST_ITEMS = 4,LIST_BRANCH_MENU = 5,HELP = 6, RESTAURANT_SCEDULE= 7,BRANCH_CATEGORIES = 8;
    public static final String KEY_LIST_RESTAURANT  = "want", KEY_BRANCH  = "res_id",KEY_MENU  = "menu_id";
    public static final String[] KEY_LIST__FOOD = {"souvlakia", "burger" ,"sandwich"};

    private AbstractResponse mapKeywordsToResponse(){

        ArrayList<String> KeyWords = new ArrayList<>();
        String response_message= "No response";

        AbstractResponse abstract_response = null;


        switch (msgCode) {

            case LIST_RESTAURANT:

                /*---Setting categories codes according to keywords ----*/

                    if (keyWords.contains(KEY_LIST__FOOD[1]))
                        sc.addCategory(ServiceCaller.BURGERS);



                    if (keyWords.contains(KEY_LIST__FOOD[2]))
                        sc.addCategory(ServiceCaller.SANDWICH);


                    if (keyWords.contains(KEY_LIST__FOOD[0]))
                        sc.addCategory(ServiceCaller.SOUVLAKIA);

                        abstract_response = new ListOfRestaurantNamesResponse();


                        break;

            case SHOW_HISTORY:

                abstract_response = new ShowHistoryResponse();

                break;

            case CHOOSE_PAYMENT:
                break;

            case LIST_ITEMS

                abstract_response = new ChoosePaymentMethodResponse();
                ((ListIngredientResponse)abstract_response).setRestaurantId(selectedMenuIDs);
                abstract_response = new ChoosePaymentMethodResponse();

                break;

            case LIST_BRANCH_MENU:

                abstract_response  = new ListBranchMenu();
                ((ListBranchMenu)abstract_response).setRestaurantId(selectedRestaurant);

                break;

            case HELP:
                abstract_response = new HelpResponse();
                break;

            case RESTAURANT_SCEDULE:
                abstract_response = new ListRestaurantScheduleResponse();

                break;
            case BRANCH_CATEGORIES:

                abstract_response = new ListBranchFoodCategoriesResponse();


                break;



        }


        abstract_response.setServiceCaller(sc);
        return abstract_response;
    }

    private void setMsgCode(int MsgCode){


            this.msgCode = MsgCode;

    }
    private void setServiceCaller(ServiceCaller sc){


        this.sc = sc;

    }
    private void setKeyWords (List<String> keyWords){


        this.keyWords= keyWords;

    }


    public void produceMessageCode (List<String> keyWords){

        for(String food : KEY_LIST__FOOD)
          if (keyWords.contains(food) || keyWords.contains(KEY_LIST_RESTAURANT)) {
              setMsgCode(LIST_RESTAURANT);
              return;
          }

            if (keyWords.contains(KEY_BRANCH)) {
                setMsgCode(LIST_BRANCH_MENU);
                setselectedRest( Integer.parseInt(keyWords.get(LIST_RESTAURANT)) );
                return;
        }
       else
            setMsgCode(HELP);
            return;


    }
    public  void  setselectedRest(int sR){

        this.selectedRestaurant = sR;


    }


    public String getResponce (List<String> keyWords,ServiceCaller sc){

       setServiceCaller(sc);
       System.out.println(keyWords);
       setKeyWords (keyWords);
       produceMessageCode(keyWords);


       return   mapKeywordsToResponse().getResponse();




    }


}
