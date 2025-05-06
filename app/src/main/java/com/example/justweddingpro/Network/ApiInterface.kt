package com.example.justweddingpro.Network

import com.example.justweddingpro.ClientUi.Request.AddOrderRequest
import com.example.justweddingpro.ClientUi.Request.FeedBackRequest
import com.example.justweddingpro.ClientUi.Request.OrderStatusChange
import com.example.justweddingpro.ClientUi.Response.EventFunctionMenuDetailsResponse
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.ClientUi.Response.UpcomingFunctionListResponse
import com.example.justweddingpro.ManagerAndCaptainUi.Response.OrderListTableResponse
import com.example.justweddingpro.Network.RequestModel.AddCategoryRequest
import com.example.justweddingpro.Network.RequestModel.AddEventMenuPlanRequest
import com.example.justweddingpro.Network.RequestModel.AddEventRequest
import com.example.justweddingpro.Network.RequestModel.AddFunctionRequest
import com.example.justweddingpro.Network.RequestModel.AddItemsRequest
import com.example.justweddingpro.Network.RequestModel.AddPartyRequest
import com.example.justweddingpro.Network.RequestModel.AddTableRequest
import com.example.justweddingpro.Network.RequestModel.AddUserRequest
import com.example.justweddingpro.Network.RequestModel.FunctionAssignRequest
import com.example.justweddingpro.Network.RequestModel.LoginRequest
import com.example.justweddingpro.Network.RequestModel.ManagerTableAssignRequest
import com.example.justweddingpro.Network.RequestModel.RegisterRequest
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.Response.AddItemsResponse
import com.example.justweddingpro.Response.AssignFunctionResponse
import com.example.justweddingpro.Response.FunctionDetailsResponse
import com.example.justweddingpro.Response.LoginResponse
import com.example.justweddingpro.Response.ManagerListResponse
import com.example.justweddingpro.Response.MenuCategoryListResponse
import com.example.justweddingpro.Response.PartyDetailsResponse
import com.example.justweddingpro.Response.RegisterResponse
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.ui.Response.AddFunctionResponse
import com.example.justweddingpro.ui.Response.AddUserResponse
import com.example.justweddingpro.ui.Response.AssignManagerAndCaptainResponse
import com.example.justweddingpro.ui.Response.EvenMenuPlanningDetails
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.EventListResponse
import com.example.justweddingpro.ui.Response.FunctionListResponse
import com.example.justweddingpro.ui.Response.MenuItemDetailsResponse
import com.example.justweddingpro.ui.Response.MenuPlanningResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface ApiInterface {

    @POST(APIConfig.API_LOGIN)
    fun Login(
        @Body body: LoginRequest
    ): Call<ResponseBase<LoginResponse>>

    @POST(APIConfig.API_ADD_EVENT)
    fun AddEvent(
        @Body body: AddEventRequest
    ): Call<ResponseBase<AddEventResponse>>

    @GET(APIConfig.API_Party_Details)
    fun GetPartyDetails(
        @Path("input") input: String
    ): Call<ResponseBase<PartyDetailsResponse>>

    @GET(APIConfig.API_Function_Details)
    fun GetFunctionDetails(
        @Path("input") input: String
    ): Call<ResponseBase<FunctionDetailsResponse>>

    @GET(APIConfig.API_Event_Menu_Details)
    fun API_Event_Menu_Details(
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String
    ): Call<ResponseBase<EventFunctionMenuDetailsResponse>>

    @GET(APIConfig.API_GET_ORDER_TABLE)
    fun API_GETOrderListByTableDetails(
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String,
//        @Path("clientid") clientid: String
    ): Call<ResponseBase<OrderListTableResponse>>

    @GET(APIConfig.API_GET_MANAGER_BYID_TABLE)
    fun API_GET_Manager_TableList(
        @Path("clientid") clientid: String,
        @Path("functionid") functionid: String
    ): Call<ResponseBase<ManagerTableListResponse>>

    @POST(APIConfig.API_ADD_OrderTable)
    fun API_AddOrder(
        @Body body: AddOrderRequest
    ): Call<ResponseBase<AddEventResponse>>

    @POST(APIConfig.API_FEEDBACK)
    fun API_Feedback(
        @Body body: FeedBackRequest
    ): Call<ResponseBase<AddEventResponse>>

    @POST(APIConfig.API_CHANGE_ORDERTABLE_STATUS)
    fun API_ORDER_Status_Change(
        @Body body: OrderStatusChange
    ): Call<ResponseBase<AddEventResponse>>

    @GET(APIConfig.API_MENU_PLANNING)
    fun GET_Menu_Planning_List(
        @Path("clientid") clientid: String,
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String
    ): Call<ResponseBase<MenuPlanningResponse>>

    @GET(APIConfig.API_MENU_PLANNING_ITEMS)
    fun GET_Menu_Planning_Item_List(
        @Path("page") page: String,
        @Path("totalItem") totalItem: String,
        @Path("clientid") clientid: String,
        @Path("menucatId") menucatId: String,
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String
    ): Call<ResponseBase<MenuItemDetailsResponse>>

    @POST(APIConfig.API_AddMenuPlanning)
    fun API_AddMenuPlanning(
        @Body body: AddEventMenuPlanRequest
    ): Call<ResponseBase<AddEventResponse>>

    @GET(APIConfig.API_FUNCTION_LISTS)
    fun GET_Function_List(
        @Path("eventid") eventid: String,
    ): Call<ResponseBase<FunctionListResponse>>

    @GET(APIConfig.API_EVENT_DETAILS)
    fun GET_EventDetails(
        @Path("eventid") eventid: String,
    ): Call<ResponseBase<EventDetailsResponse>>

    @GET(APIConfig.API_EventList)
    fun GET_EventList(
        @Path("clientid") clientid: String,
    ): Call<ResponseBase<EventDetailsResponse>>

    @GET(APIConfig.API_GET_ALLEvent_byClientId)
    fun GET_CalenderEventList(
        @Path("clientid") clientid: String,
    ): Call<ResponseBase<EventListResponse>>

    @POST(APIConfig.API_AddUser)
    fun API_AddUser(
        @Body body: AddUserRequest
    ): Call<ResponseBase<AddUserResponse>>

    @POST(APIConfig.API_AddItems)
    fun API_AddItems(
        @Body body: AddItemsRequest
    ): Call<ResponseBase<AddItemsResponse>>

    @GET(APIConfig.API_GetMenuCategory)
    fun API_GetMenuCategory(
        @Path("clientid") clientid: String,
    ): Call<ResponseBase<MenuCategoryListResponse>>

    @POST(APIConfig.API_UploadFile)
    @Multipart
    fun API_UploadFile(
        @Part file: MultipartBody.Part,
        @Part("moduleId") moduleId: RequestBody,
        @Part("moduleName") moduleName: RequestBody,
        @Part("fileType") fileType: RequestBody,
    ): Call<ResponseBase<MenuCategoryListResponse>>

    @POST(APIConfig.API_Register)
    fun API_RegisterUser(
        @Body body: RegisterRequest
    ): Call<ResponseBase<RegisterResponse>>

    @POST(APIConfig.API_IsApprove)
    fun API_IsApprove(
        @Path("clientid") clientid: String
    ): Call<ResponseBase<RegisterResponse>>

    @GET(APIConfig.API_ManagerAndCaptainList)
    fun API_GetManagerAndCaptain(
        @Path("clientid") clientid: String,
        @Path("labelname") labelname: String
    ): Call<ResponseBase<ManagerListResponse>>

    @POST(APIConfig.API_AddAssignManager)
    fun API_AddAssignUser(
        @Body body: FunctionAssignRequest
    ): Call<ResponseBase<RegisterResponse>>

    @GET(APIConfig.API_GetAssignManagerCaptain)
    fun API_GetAssignManagerCaptain(
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String
    ): Call<ResponseBase<AssignManagerAndCaptainResponse>>

    @GET(APIConfig.API_GetTableList)
    fun API_GetTableList(
        @Path("clientid") clientid: String
    ): Call<ResponseBase<TableListResponse>>

    @POST(APIConfig.API_ManagerTableAssign)
    fun API_UserTableAssign(
        @Body body: ManagerTableAssignRequest
    ): Call<ResponseBase<TableListResponse>>

    @GET(APIConfig.API_MenuPlanningReport)
    fun API_getPdfReport(
        @Path("clientid") clientid: String,
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String
    ): Call<ResponseBase<TableListResponse>>

    @POST(APIConfig.API_AddMenuCategory)
    fun API_AddCategory(
        @Body body: AddCategoryRequest
    ): Call<ResponseBase<TableListResponse>>

    @POST(APIConfig.API_AddTable)
    fun API_AddTable(
        @Body body: AddTableRequest
    ): Call<ResponseBase<TableListResponse>>

    @POST(APIConfig.API_AddPartyAccount)
    fun API_AddPartyAccount(
        @Body body: AddPartyRequest
    ): Call<ResponseBase<TableListResponse>>

    @GET(APIConfig.API_GetFunctionAssign)
    fun API_GetFunctionAssignList(
        @Path("clientid") clientid: String,
    ): Call<ResponseBase<AssignFunctionResponse>>

    @DELETE(APIConfig.API_GetDeleteFunction)
    fun API_GetDeleteFunction(
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String,
    ): Call<ResponseBase<TableListResponse>>

    @DELETE(APIConfig.API_GetDeleteAssignFunction)
    fun API_GetDeleteAssignFunction(
        @Path("managerid") eventid: String,
    ): Call<ResponseBase<TableListResponse>>

    @GET(APIConfig.API_GetUpcomingFunction)
    fun API_GetUpcomingFunction(
        @Path("clientid") clientid: String,
    ): Call<ResponseBase<UpcomingFunctionListResponse>>

    @GET(APIConfig.API_GetMenuPlanningDetails)
    fun API_GetMenuPlanningDetails(
        @Path("eventid") eventid: String,
        @Path("functionid") functionid: String,
    ): Call<ResponseBase<EvenMenuPlanningDetails>>

    @POST(APIConfig.API_AddFunctionMaster)
    fun API_AddFunctionMaster(
        @Body body: AddFunctionRequest
    ): Call<ResponseBase<AddFunctionResponse>>

}