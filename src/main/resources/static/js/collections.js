class CollectionReqParam{
    //싱글톤
    static #instance = null;
    #page = 0;

    constructor() {
        this.#page = 1;
    }

    static getInstance(){
        if(this.#instance == null){
            this.#instance = new CollectionReqParam();
        }
        return this.#instance;
    }

    getPage(){return this.#page;}
    setPage(page){this.#page = page;}

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

    groupIdList = new Array();

    loadCollections() {
        const responseData = CollectionsApi.getInstance().getCollections(CollectionReqParam.getInstance().getObject());
        const collectionProducts = document.querySelector(".collection-products");
        responseData.forEach(collection => {
            this.groupIdList.push(collection.groupId);
            collectionProducts.innerHTML += `
                <li class="collection-product">
                    <div class="product-img">
                        <img src="/image/product/${collection.imgName}">
                    </div>
                    <div class="product-name">${collection.name}</div>
                    <div class="product-price">${collection.price}원</div>
                    <div class="product-group">그룹아이디 ${collection.groupId}</div>
                </li>
            `;
        });
        this.addProductClickEvent();
        this.addScrollEvent();
    }



    addScrollEvent(){
        const html = document.querySelector('html');
        const body = document.querySelector('body');
        //
        body.onscroll = () =>{
            let scrollStatus = body.offsetHeight - html.clientHeight - html.scrollTop;
            if(scrollStatus > -1 && scrollStatus < 30){
                CollectionReqParam.getInstance().setPage(Number(CollectionReqParam.getInstance().getPage()) + 1); //페이지 1 늘려서 다시 loadCollections
                CollectionsService.getInstance().loadCollections();
            }
        }
    }

    addProductClickEvent(){
        const products = document.querySelectorAll(".collection-product");

        products.forEach((product, index) => {
            product.onclick = () =>{
                location.href = `/products/${this.groupIdList[index]}`; //responseData[index]. 의 id가 상품 id
            }
        })

    }
}

window.onload =() =>{
    CollectionsService.getInstance().loadCollections();



}