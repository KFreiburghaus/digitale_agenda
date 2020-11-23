import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthControllerService} from './api/authController.service';
import {lifecycleHookToNodeFlag} from '@angular/compiler/src/view_compiler/provider_compiler';
import {catchError, retry} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {
  authControllerService: any;

  constructor(private injector: Injector) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (req.url === 'http://localhost:8080/calendar') {
       this.authControllerService = this.injector.get(AuthControllerService);

      let tokenizedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.getToken()}`
          // Authorization: 'Bearer ' + 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYXJiZXJAc2hvcC5jb20iLCJpYXQiOjE2MDMwMjI1NTYsImV4cCI6MTYwMzA1ODU1Nn0.77Ucr66uI5WRh8hctxnzqlioJURhRqGfNoqEAGUPxaE'
        }
      });
      return next.handle(tokenizedReq);
    }
    else return next.handle(req);

  }
  getToken() {
    return localStorage.getItem('token');
  }

}
