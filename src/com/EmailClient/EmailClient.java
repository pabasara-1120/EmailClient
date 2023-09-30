package com.EmailClient;

import java.io.*;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;


public class EmailClient {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Enter option type:\s
                1 - Adding a new recipient
                2 - Sending an email
                3 - Printing out all the recipients who have birthdays
                4 - Printing out details of all the emails sent
                5 - Printing out the number of recipient objects in the application""");

        int option = scanner.nextInt();

        switch (option) {
            case 1 -> {                             // code to add a new recipient
                System.out.println("""
                        Enter recipient data:\s
                        Official: nimal,nimal@gmail.com,ceo\s
                        Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12\s
                        Personal: sunil,<nick-name>,sunil@gmail.com,2000/10/10""");
                Scanner input1 = new Scanner(System.in);
                String type = input1.nextLine();
                try {                           // code to add a new recipient
                    FileWriter Writer = new FileWriter("clientList", true);
                    Writer.write(type + "\n");
                    Writer.close();
                    System.out.println("Entry successfully entered \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            case 2 -> {
                System.out.println("Enter email address,subject and emailBody \n"); //sending an email
                Scanner input2 = new Scanner(System.in);
                String mail = input2.nextLine();
                String[] arrOfData = mail.split(",");

                // code to send an email
                SendEmail newEmail = new SendEmail(arrOfData[0], arrOfData[1]); //recipient and subject
                newEmail.sendMessage(arrOfData[2]); //mailBody
                System.out.println("Email sent");
                SerializeObjects serializeObject1 = new SerializeObjects();
                serializeObject1.objectSerialization(newEmail);
                break;
            }
            case 3 -> {
                System.out.println("Enter the data: \n date format 2018/09/17"); //printing recipient who have Birthdays on input date
                Scanner input3 = new Scanner(System.in);
                String date = input3.nextLine();
                File ob = new File("./clientList");
                Scanner reader = new Scanner(ob);
                List<String> arrayList = new ArrayList<>();
                while (reader.hasNextLine()) {
                    String info = reader.nextLine();
                    arrayList.add(info);
                }
                int size = arrayList.size();
                for (String line : arrayList) {
                    String[] entries = line.split(","); //Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12
                    int enSize = entries.length;
                    if (enSize == 4) {
                        if (((entries[3]).substring(5, 10)).equals((date).substring(5, 10))) {
                            String name = entries[0].split(":")[1];
                            System.out.println(name);
                        }
                    }


                }
                break;
            }
            case 4 -> {
                System.out.println("Enter the data: \n date format 2018/09/17"); //
                Scanner input4 = new Scanner(System.in);
                String dateTo = input4.nextLine();
                String serFileName="file.ser";              // code to print the details of all the emails sent on the input date
                File myFile=new File(serFileName);
                if(myFile.exists()){
                    FileInputStream fIStream=new FileInputStream("file.ser");
                    boolean flag=true;
                    SendEmail mail=null;
                    try {
                        ObjectInputStream oiStream=new ObjectInputStream(fIStream);
                        while(flag) {
                            try{
                                mail=(SendEmail)oiStream.readObject();
                            }
                            catch (Exception exception) {
                                flag = false;
                            }
                            assert mail != null;
                            if(mail.date.equals(dateTo)) //check if the input date is mail sent date
                                System.out.println("Recipient: "+mail.recipient+"\n"+"Subject: "+mail.subject);//print details


                        }
                        oiStream.close();
                        fIStream.close();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    }
                     
                }

                case 5 -> {
                try {// code to print the number of recipient objects in the application
                    File ob = new File("./countList");
                    Scanner readFile = new Scanner(ob);
                    while (readFile.hasNextLine()) {
                        String data = readFile.nextLine();
                        System.out.println(data);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(0);
                }

            }
        }
        List<Recipient> bdayHolders=new ArrayList<Recipient>();

        Official official=null;
        OfficeFriend officeFriend=null;
        Personal personal=null;

        LocalDate dateObject=LocalDate.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date=dateObject.format(dateTimeFormatter);

        try {
            String file="clientList";
            File myFile=new File(file);
            List<Official> officialList=new ArrayList<Official>();  //arrays to store the objects created
            List<OfficeFriend> officeFriendList=new ArrayList<OfficeFriend>();
            List<Personal> personalList=new ArrayList<Personal>();



            FileInputStream fileInputStream=new FileInputStream("clientList"); //reading the clientList file
            BufferedReader br=new BufferedReader(new InputStreamReader(fileInputStream));
            String dataEntry;

            while((dataEntry=br.readLine()) !=null){
                String[] entryLine=dataEntry.split(":");


                switch (entryLine[0]) {
                    case "Official" -> { //if official create official object and add to arraylist
                        String[] recipientInfo = entryLine[1].split(",");
                        official = new Official(recipientInfo[0], recipientInfo[1], recipientInfo[2]);
                        officialList.add(official);

                    }
                    case "Office_friend" -> {
                        String[] recipientInfo = entryLine[1].split(","); //if office friend add to office friend arrayList
                        officeFriend = new OfficeFriend(recipientInfo[0], recipientInfo[1], recipientInfo[2], recipientInfo[3]);
                        officeFriendList.add(officeFriend);
                        if(officeFriend.checkBirthday(date.substring(5,10))){ //if office friend has bday today add to bdayholders list
                            bdayHolders.add(officeFriend);
                        }
                    }
                    case "Personal" -> {
                        String[] recipientInfo = entryLine[1].split(","); //if personal add to personal arrayList
                        personal = new Personal(recipientInfo[0], recipientInfo[1], recipientInfo[2], recipientInfo[3]);
                        personalList.add(personal);


                        if(personal.checkBirthday(date.substring(5,10))){ //if personal has bday today add to bdayholder list
                            bdayHolders.add(personal);

                        }

                    }

                }


            }
        }
        catch (FileNotFoundException ee){
            ee.printStackTrace();
        }

        int count=Recipient.noOfObjects; //no of objects created
        try {
            FileWriter Writer = new FileWriter("countList", false);
            Writer.write(count + "\n");
            Writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        SerializeObjects serializeObjects=new SerializeObjects();
        SendEmail newEmail;








        for (Recipient bdayHolder : bdayHolders) { //send bday emails iterating bdayHolders list
            if (bdayHolder instanceof OfficeFriend) {


                assert officeFriend != null;
                newEmail = new SendEmail(officeFriend.getEmail(), "Your Birthday!"); //bday wish to office friend recepient
                newEmail.sendMessage("Wish you a Happy Birthday! \nPabasara");
                System.out.println(" Bday email sent to "+bdayHolder.getName());

                serializeObjects.objectSerialization(newEmail);
            } else if (bdayHolder instanceof Personal) {


                assert personal != null;
                newEmail = new SendEmail(personal.getEmail(), "Your Birthday");//bday wish sent to personal friend
                newEmail.sendMessage("Hugs and love on your birthday.\nPabasara");
                System.out.println("Bday email sent to "+bdayHolder.getName());

                serializeObjects.objectSerialization(newEmail);


                }
            }


        }

    }



















