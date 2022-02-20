import { Component, Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpResponse, HttpRequest, HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-token-interceptor',
  templateUrl: './token-interceptor.component.html',
  styleUrls: ['./token-interceptor.component.css']
})
@Injectable()
export class TokenInterceptorComponent implements HttpInterceptor {

  constructor(private cookieService: CookieService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        'Authorization': `Bearer ${this.cookieService.get('auth-token')}`,
      },
    });
    return next.handle(request);
  }

}
