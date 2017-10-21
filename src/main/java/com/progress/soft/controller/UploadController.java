package com.progress.soft.controller;

import com.progress.soft.FileUploaderUtils;
import com.progress.soft.config.AppConfig;
import com.progress.soft.model.TransactionModel;
import com.progress.soft.service.TransactionOperationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by inshaf on 10/19/17.
 */


@Controller
public class UploadController {

    private Log logger = LogFactory.getLog(UploadController.class);

    @RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    public ModelAndView printWelcome(Model model) {

        String redirectMessage = (String) model.asMap().get("message");
        //logs debug message
        if (logger.isDebugEnabled()) {
            logger.debug("Index page is hit");
        }

        if (null == redirectMessage || redirectMessage.isEmpty()){
            redirectMessage = "Upload your file";
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", redirectMessage);
        modelAndView.setViewName("uploader");
        return modelAndView;
    }


    /**
     * Upload single file using Spring Controller and JPA EntityManager nativeQuery.
     * This is the fastest way to upload 100k records. Upload happens in less than 5 seconds
     */
    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView uploadFileHandler(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        ModelAndView model = new ModelAndView();

        long startTime = System.currentTimeMillis();

        TransactionOperationService transactionService = context.getBean(TransactionOperationService.class);

        List<String> fileList = transactionService.listOfDistinctFile();

        if(!fileList.contains(file.getOriginalFilename())){
            if (!file.isEmpty()) {
                try {
                    String line;
                    String cvsSplitBy = ",";

                    InputStream is = file.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    StringBuilder stringBuilderValidStocks = new StringBuilder();
                    StringBuilder stringBuilderInvalidStocks = new StringBuilder();

                    String separator="",separatorInvalid = "";

                    boolean isvalidRecordsAvailable = false;
                    boolean isInvalidRecordsAvailable = false;

                    while ((line = br.readLine()) != null) {

                        String[] stocks = line.split(cvsSplitBy);

                        if(stocks.length == 5){
                            String id = stocks[0];
                            String fromCurrency = stocks[1];
                            String toCurrency = stocks[2];
                            String formattedTimestamp = stocks[3].replaceAll("\"", "");
                            String amount = stocks[4];
                            String fileName = file.getOriginalFilename();


                            if(FileUploaderUtils.validateNullAndEmpty(fromCurrency, toCurrency, formattedTimestamp, amount, fileName)){

                                isvalidRecordsAvailable = true;

                                stringBuilderValidStocks.append(separator + "( '" + id + "' , '" + fromCurrency + "' , '" + toCurrency + "' , '" + formattedTimestamp
                                        + "' , '" + Double.parseDouble(amount) + "' , '" + fileName + "')");

                                separator = ",";

                            }else{

                                isInvalidRecordsAvailable = true;
                                stringBuilderInvalidStocks.append(separatorInvalid + "( '" + id + "' , '" + fromCurrency + "," + toCurrency + "," + formattedTimestamp
                                        + "," + amount + "' , '" + fileName + "')");

                                separatorInvalid = ",";

                            }
                        }

                    }
                    if(isvalidRecordsAvailable) {
                        transactionService.uploadNativeQuery(stringBuilderValidStocks);
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("Valid records successfully submitted");
                    }

                    if(isInvalidRecordsAvailable) {
                        transactionService.uploadInvalidNativeQuery(stringBuilderInvalidStocks);
                    }

                    if (logger.isDebugEnabled()) {
                        logger.debug("Invalid records successfully submitted");
                    }

                    context.close();

                    logger.info("You successfully uploaded file=" + file.getName());

                    long endTime   = System.currentTimeMillis();
                    long totalTime = endTime - startTime;

                    if (logger.isDebugEnabled()) {
                        logger.debug("Time taken to execute :" +totalTime/1000 + "seconds");
                    }

                    redirectAttributes.addFlashAttribute("message", "Successfully uploaded " + file.getOriginalFilename() + ". Time taken to execute: " + totalTime / 1000 + " seconds");

                }
                catch (ConstraintViolationException e){
                    logger.error("You failed to upload " + file.getName() + ", already submitted. Error: " + e.getMessage());
                    redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getName() + ". Error: File records are already in the DB");
                }
                catch (Exception e) {
                    logger.error("Failed to upload " + file.getOriginalFilename() + ". Error: " + e.getMessage());
                    redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getName() + ". Error " + e.getMessage());
                }


            } else {
                model.addObject("message", "Failed to upload " + file.getOriginalFilename() + " because the file was empty.");
            }
        }else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getName() + ". Error: File records are already in the DB");

        }

        model.setViewName("redirect:/fileUpload");
        return model;

    }



    /**
     * Upload single file using Spring Controller and JPA EntityManager
     * This methods works, but takes nearly 50 seconds to process
     */
    @RequestMapping(value = "/doUploadUsingModel", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView uploadFileHandlerWithEntityManager(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        ModelAndView model = new ModelAndView();

        TransactionOperationService transactionService = context.getBean(TransactionOperationService.class);

        long startTime = System.currentTimeMillis();
        if (!file.isEmpty()) {
            try {
                String line;
                String cvsSplitBy = ",";

                InputStream is = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                List<TransactionModel> validModels = new ArrayList<>();
                List<TransactionModel> invalidModels = new ArrayList<>();

                while ((line = br.readLine()) != null) {

                    String[] stocks = line.split(cvsSplitBy);
                    String id = stocks[0];
                    String fromCurrency = stocks[1];
                    String toCurrency = stocks[2];
                    String formattedTimestamp = stocks[3].replaceAll("\"", "");
                    String amount = stocks[4];
                    String fileName = file.getOriginalFilename();


                    if(FileUploaderUtils.validateNullAndEmpty(fromCurrency, toCurrency, formattedTimestamp, amount, fileName)){

                        validModels.add(new TransactionModel(Long.parseLong(id),fromCurrency, toCurrency, formattedTimestamp, Double.parseDouble(amount), fileName));

                    }else{
                        if (logger.isDebugEnabled()) {
                            logger.debug("Invalid Record, ID :" +id);
                        }

                        invalidModels.add(new TransactionModel(Long.parseLong(id), fromCurrency, toCurrency, formattedTimestamp, Double.parseDouble(amount), fileName));

                    }
                }

                transactionService.upload(validModels);

                transactionService.upload(invalidModels);

                context.close();

                long endTime   = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                logger.info("You successfully uploaded file=" + file.getName());

                if (logger.isDebugEnabled()) {
                    logger.debug("Time taken to execute :" +totalTime/1000 + "seconds");
                }

                redirectAttributes.addFlashAttribute("message", "Successfully uploaded " + file.getName() + ". Time taken to execute :" + totalTime / 1000 + "seconds");


            }
            catch (ConstraintViolationException e){
                logger.error("You failed to upload " + file.getName() + " => " + e.getMessage());
                redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getName() + ". Error: File records are already in the DB");
            }

            catch (Exception e) {
                logger.error("You failed to upload " + file.getName() + " => " + e.getMessage());
                redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getName() + ". Error: " + e.getMessage());
            }

        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getName() + " because the file was empty.");
        }


        model.setViewName("redirect:/fileUpload");
        return model;

    }

}
