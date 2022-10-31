class ImportApi{
    static #instance = null;
    static getInstance (){
        if(this.#instance == null){
            this.#instance = new ImportApi()
        }
        return this.#instance;
    }

    IMP = null;

    #importPayParams = {
        pg: "kakaopay",
        pay_method: "card",
        merchant_uid: "product-" + new Date().getTime(),
        name: "노르웨이 회전 의자",
        amount: 1,
        buyer_email: "gildong@gmail.com",
        buyer_name: "홍길동",
        buyer_tel: "010-4242-4242",
        buyer_addr: "서울특별시 강남구 신사동",
        buyer_postcode: "01181"
    }

    impInfo = {
        impUid: null,
        restApikey: null,
        restApiSecret: null
    }

    constructor(){
        this.IMP = window.IMP; //생략가능
        this.impInfo.impUid = "imp04560234"
        this.impInfo.restApikey = "2825554727181500"
        this.impInfo.restApiSecret = "JWLH04h7k68rG77Hz83iSaEk9YIR7YLZ7ok29HF0WCtIAutfq6c0txJAjJYQCmTNXhTK271srhxdM7iX"
        this.IMP.init(this.impUid); //가맹점 식별 코드
    }

    requestPay() {
        this.IMP.request_pay(this.#importPayParams, this.responsePay); //메소드 호출이 아니라 이름만 넣어준다...?
    }

    //callback 함수
    responsePay(resp){
        if(resp.success){
            alert("결제 성공!");
            this.requestPayDetails()

        }else{
            alert("결제 실패!");
        }
    }

    requestImpAccessToken() {
        let accessToken = null;

        $.ajax({
            async: false,
            type: "post",
            url: "https://api.iamport.kr/users/getToken",
            contentType:"application/json",
            data: JSON.stringify({
                imp_key: this.impInfo.restApikey,
                imp_secret: this.impInfo.restApiSecret
            }),
            dataType: "json",
            success: (response) => {
                accessToken = response;
            },
            error: (error) => {
                console.log(error)
            }
        });
        return accessToken;
    }
    requestPayDetails() {
        const accessToken = this.requestImpAccessToken();
        console.log(accessToken);
    }


}

class Order{
    addPaymentButtonEvent(){
        const paymentButton = document.querySelector(".payment-button");
        paymentButton.onclick = () => {
            ImportApi.getInstance().requestPay();
        }
    }
}

window.onload = () => {

    new Order();
}