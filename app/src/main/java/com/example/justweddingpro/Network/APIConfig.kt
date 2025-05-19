package com.example.justweddingpro.Network

object APIConfig {

    const val WEB_URL = "https://justwedding.in/WS/"
    const val VERSION = "v1/"

    /*userName:- AppUser
    password:- AppUser@123_4*/

    const val SERVER_URL = "${WEB_URL}"

    const val API_LOGIN = "loginclientuser/"
    const val API_ADD_EVENT = "addeventmaster/"
    const val API_Party_Details = "getpartymasterbyclientid/{input}/"
    const val API_Function_Details = "getfunctionmasterbyclientid/{input}/"

    const val API_Event_Menu_Details =
        "geteventmenuplandetailsbyeventandfunctionid/{eventid}/{functionid}/"
    const val API_GET_ORDER_TABLE = "getordertablebyeventandfunctionid/{eventid}/{functionid}/"
    const val API_GET_MANAGER_BYID_TABLE =
        "getmanagertableassignbyclientandfunctionid/{clientid}/{functionid}/"
    const val API_ADD_OrderTable = "addordertable/"
    const val API_FEEDBACK = "addfeedback/"
    const val API_CHANGE_ORDERTABLE_STATUS = "changeordertablestatus/"

    const val API_MENU_PLANNING =
        "getitemdetailsbyclientandeventandfunctionid/{clientid}/{eventid}/{functionid}/"
    const val API_MENU_PLANNING_ITEMS =
        "getitemdetailsbypagenototalrecordclientidmenucategoryideventidfunctionid/" +
                "{page}/{totalItem}/{clientid}/{menucatId}/{eventid}/{functionid}/"

    const val API_FUNCTION_LISTS = "geteventfunctionbyeventid/{eventid}/"
    const val API_EVENT_DETAILS = "geteventmasterbyid/{eventid}/"
    const val API_EventList = "geteventmasterbyclientid/{clientid}/"
    const val API_AddMenuPlanning = "addeventmenuplan/"
    const val API_GET_ALLEvent_byClientId = "getalleventsbyclientid/{clientid}/"

    const val API_AddUser = "adduser/"
    const val API_AddItems = "additem/"
    const val API_GetMenuCategory = "getmenucategorybyclientid/{clientid}/"
    const val API_UploadFile = "uploadfile/"
    const val API_Register = "registerclientuser/"
    const val API_IsApprove = "isapprove/{clientid}/"
    const val API_ManagerAndCaptainList = "getsiteuserdetailsbyclientid/{clientid}/{labelname}/"
    const val API_AddAssignManager = "addfunctionmangerassign/"
    const val API_GetAssignManagerCaptain =
        "getfunctionmanagerassignbyeventidandfunctionid/{eventid}/{functionid}/"

    const val API_GetTableList = "gettablebyclientid/{clientid}/"
    const val API_ManagerTableAssign = "addmanagertableassign/"
    const val API_MenuPlanningReport = "generatemenuplanreport/{clientid}/{eventid}/{functionid}/"
    const val API_AddMenuCategory = "addmenucategory/"
    const val API_AddTable = "addtable/"
    const val API_AddPartyAccount = "addpartyaccountmaster/"
    const val API_GetFunctionAssign = "getfunctionmanagerassignbyclientId/{clientid}/"
    const val API_GetDeleteFunction = "deleteeventfunctionbyeventandfunctionid/{eventid}/{functionid}/"
    const val API_GetDeleteAssignFunction = "deletefunctionmanagerassignbyid/{managerid}/"
    const val API_GetDeleteAssignTable = "deletemanagertableassignbyid/{managerid}/"
    const val API_GetUpcomingFunction = "getupcommingfunctionmanagerassignbyclientId/{clientid}/"
    const val API_GetMenuPlanningDetails = "geteventmenuplandetailsbyeventandfunctionid/{eventid}/{functionid}/"
    const val API_AddFunctionMaster = "addfunctionmaster/"

    const val API_GetEventDelete = "deleteeventmasterbyid/{eventid}/"

    /*Api Keys*/
    object ApiConst {
        const val Authorization = "Authorization"
    }
}