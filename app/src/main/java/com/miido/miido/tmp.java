package com.miido.miido;

/**
 * Created by Alvaro on 18/02/2015.
 */
public class tmp {

}
/*

public final String[][] structure =
            {
                    {
                            "1",            //id - order
                            "tDoc",         //name
                            "Tipo Documento",//label
                            "sp",           //type
                            "true",         //required
                            "0",            //options Id
                            "Tipo Documento",//hint
                            "0",            //input rules
                            "0",            //Max Length
                            "0",            //parent
                            "1",            //form
                            "0",            //Sub-form
                            "1",            //category
                            "1",            //Taxonomy
                            "0",            //handler id
                            "1"             //age range
                    },
                    {
                            "2",            //id - order
                            "Documento",    //name
                            "Documento",    //label
                            "tf",           //type
                            "true",         //required
                            "0",            //options Id
                            "N° Documento", //hint
                            "vch",          //input rules
                            "20",           //Max Length
                            "0",            //parent
                            "1",            //form
                            "0",            //Sub-form
                            "1",            //category
                            "1",            //Taxonomy
                            "0",            //handler type
                            "1"             //age range
                    },
                    {
                            "3",            //id - order
                            "name",         //name
                            "Nombre",       //label
                            "tf",           //type
                            "true",         //required
                            "1",            //options Id
                            "Nombre",       //hint
                            "vch",          //input rules
                            "50",           //Max Length
                            "null",         //parent
                            "0",            //Sub-form
                            "1",            //form
                            "1",            //category
                            "1",            //Taxonomy
                            "0",            //handler type
                            "1"             //age range
                    }/*,
                    {
                            "4",            //id - order
                            "lname",        //name
                            "Apellido",     //label
                            "tf",           //type
                            "true",         //required
                            "1",            //options Id
                            "Apellido",     //hint
                            "vch",          //input rules
                            "50",           //Max Length
                            "0",            //parent
                            "1",            //form
                            "0",            //Sub-form
                            "1",            //category
                            "1",            //Taxonomy
                            "0",            //handler type
                            "1"             //age range
                    }*//*,
        {
        "5",            //id - order
        "BornDate",     //name
        "Fecha Nacimiento",//label
        "dp",           //type
        "true",         //required
        "1",            //options Id
        "DD/MM/AAAA",   //hint
        "date",         //input rules
        "0",            //Max Length -- never null
        "0",            //parent
        "1",            //form
        "0",            //Sub-form
        "1",            //category
        "0",            //handler type
        "1"             //age range
        },
        {
        "6",            //id - order
        "age",          //name
        "Edad",         //label
        "tf",           //type
        "true",         //required
        "1",            //options Id
        "Edad",         //hint
        "int",          //input rules
        "3",            //Max Length
        "null",         //parent
        "1",            //form
        "0",            //Sub-form
        "1",            //category
        "1",            //Taxonomy
        "1",            //handler type
        "1"             //age range
        },
        {
        "7",            //id - order
        "Email",        //name
        "Email",        //label
        "tf",           //type
        "true",         //required
        "1",            //options Id
        "Email",        //hint
        "eml",          //input rules
        "50",           //Max Length
        "null",         //parent
        "3",            //form
        "0",            //Sub-form
        "1",            //category
        "1",            //Taxonomy
        "0",            //handler type
        "1"             //age range
        },
        {
        "8",            //id - order
        "prueba",        //name
        "prueba",     //label
        "tf",           //type
        "true",         //required
        "1",            //options Id
        "Test",     //hint
        "vch",          //input rules
        "5",           //Max Length
        "0",            //parent
        "2",            //form
        "0",            //Sub-form
        "1",            //category
        "1",            //Taxonomy
        "0",            //handler type
        "1"             //age range
        }
        };
public final String[][] options =
        {
        {
        "1",            //id
        "1",            //field relation id
        //OPTIONS
        "Tarjeta de Identidad",
        "Cédula de ciudadanía",
        "Cédula de extranjería",
        "Registro civil",
        "Pasaporte",
        "Menor sin identigicación",
        "Adulto sin identificación"
        },
        {
        "2",            //id
        "4",            //field relation id
        "si",           //option 1
        "no",           //option 2
        "no sabe / no responde" //option 3
        }
        };

public final String[][] forms =
        {
        {
        "1",            //id
        "Datos básicos",//header tittle
        "0",            //has parent?
        "0"             //inside parent form?
        },
        {
        "2",            //id
        "Subformulario",//header tittle
        "6",            //switch parent (Parent == 0) = false
        "1"
        },
        {
        "3",            //id
        "Datos básicos",//header tittle
        "0",            //switch parent (Parent == 0) = false
        "0"
        }
        };
public final String[] formsOrder =     //Map of Application
        {                               //The application will show in same map order
        "1",            //Form id,
        "2",            //Form id
        "3"             //Form id
        };
public final String[][] handlerEvent =
        {
        {
        "1",            //Handler id - unique
        ">",            //Handler event type - parameter 1
        "10",           //Parameter 1
        "<",            //Handler event type - parameter 2
        "25",           //Parameter 2
        "!=",
        "19"
        }/*,
                    {
                            "9",            //Handler id - unique
                            "=",            //Handler event type - parameter 1
                            "f"             //Parameter 1
                    }*/
        /*};

 */