import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    
    intercept(req: HttpRequest<any>, 
              next: HttpHandler): Observable<HttpEvent<any>>{

          localStorage.setItem("idToken", "EkN+DOsnsuRjRO6BxXemmJDm3HbxrbRzXglbN2S4sOkopdU4IsDxTI8jO19W/A4K8ZPJijNLis4EZsHeY559a4DFOd50/OqgHGuERTqYZyuhtF39yxJPAjUESwxk2J5k/4zM3O+vtd1Ghyo4IbqKKSy6J9mTniYJPenn5+HIirE=");
          const idToken = localStorage.getItem("idToken");
          
          if(idToken){
            const cloned = req.clone({
                headers: req.headers.set("Authorization", 
                "Bearer " + idToken)
            });

            return next.handle(cloned);

          }
          else{
             return next.handle(req);
          }

    }
}