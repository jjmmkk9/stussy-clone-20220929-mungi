class CollectionsReqParams{
    //싱글톤
    static #instance = null;
    #page = 0;

    constructor() {
        this.page = 1;
    }

    static getInstance(){
        if(this.#instance == null){
            this.#instance = new CollectionsReqParams();
        }
        return this.#instance;
    }

    getPage(){return this.#page;}
    setPage(page){this.#page = page}

    getObject() {
        return{
            page: this.#page
        };
    }
}

class CollectionsApi{

    static #instance = null;

    static getInstance(){
        if(this.#instance == null){
            this.#instance = new CollectionsApi();
        }
        return this.#instance;
    }

    getCollections(collectionReqParam){
        const uri = location.href; //요청 주소
        const category = uri.substring(uri.lastIndexOf("/") + 1); //의 카테고리
        let responseData = null;
        $.ajax({
            async: false,
            type: "GET",
            url:"/api/collections/" + category,
            data: collectionReqParam, //page = this.page
            dataType: "json",
            success: (response) => {
                responseData = response.data;
                console.log(response);
            },
            error : (error) => {
                console.log(error);
            }
        });
        return responseData;
    }
}




class CollectionsService{
    static #instance = null;


    static getInstance(){
        if(this.#instance == null){
            this.#instance = new CollectionsService();
        }
        return this.#instance;
    }

    loadCollections() {
        const responseData = CollectionsApi.getInstance().getCollections(CollectionsReqParams.getInstance().getObject());
        const collectionProducts = document.querySelector(".collection-products");
        responseData.forEach(collection => {
            collectionProducts.innerHTML += `
                <li class="collection-product">
                    <div class="product-img">
                        <img src="/image/product/${collection.imgName}">
                    </div>
                    <div class="product-name">${collection.name}</div>
                    <div class="product-price">${collection.price}원</div>
                </li>
            `;
        });
        this.addProductClickEvent(responseData);
        this.addScrollEvent();
    }



    addScrollEvent(){
        const html = document.querySelector('html');
        const body = document.querySelector('body');
        //
        body.onscroll = () =>{
            let scrollStatus = body.offsetHeight - html.clientHeight - html.scrollTop;
            if(scrollStatus > -1 && scrollStatus < 30){
                CollectionsReqParams.getInstance().setPage(Number(CollectionsReqParams.getInstance().getPage()) + 1); //페이지 1 늘려서 다시 loadCollections
                CollectionsService.getInstance().loadCollections();
            }
        }
    }

    addProductClickEvent(responseData){
        const products = document.querySelectorAll(".collection-products");

        products.forEach((product, index) => {
            product.onclick = () =>{
                location.href = `/products/${responseData[index].id}`; //responseData[index]. 의 id가 상품 id
            }
        })

    }
}

window.onload =() =>{
    CollectionsService.getInstance().loadCollections();



}