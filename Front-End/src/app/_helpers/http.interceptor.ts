import {Injectable} from "@angular/core";
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StorageService} from "../_services/storage.service";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  constructor(private storage: StorageService) {
  }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let user = this.storage.getUser();
    let isLoggedIn = user && user.token;

    if (isLoggedIn) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${user?.token}`
        }
      });
    }

    return next.handle(req);
  }

}

export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true},
];
