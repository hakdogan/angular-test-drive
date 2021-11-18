import { Component, Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpResponse, HttpRequest, HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-token-interceptor',
  templateUrl: './token-interceptor.component.html',
  styleUrls: ['./token-interceptor.component.css']
})
@Injectable()
export class TokenInterceptorComponent implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        'Authorization': `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2p1Z2lzdGFuYnVsLm9yZy9pc3N1ZXIiLCJ1cG4iOiJoYWtkb2dhbiIsInVzZXJJZCI6MywiZ3JvdXBzIjpbImFkbWluIl0sImlhdCI6MTYzNzI2NDg1NSwiZXhwIjoxNjM3MjY2NjU1LCJqdGkiOiI1ZDhiZDRjZi0xNjE2LTQ4NTktYmI5Mi0xYTc0ODQxYzg4YjYifQ.Gpx2DQaxIJkniYiM-EqBEFxE0xDBOsO7W70khD6aGbAL5cxw_pc88F0lkKivmg2lGIvc4iZeWj5W6VBjIpgVcV1b0wt1jvxLr0EClOEceLc_XT84R2vmvouhnIt0XhvfjQAmoFcVfLKVZxop-ECn8VrX9G3zLNrVFgbLIB5j3Yxpy_N8mUAWwIagdLlhkgU2sgllyFIzma2ykCt1oO1clvMuSFueqLphz-kYVJephGk961oSrCLDluLD1AMDY1AjUnMT0ecw6Jf3YlEDh8YIqmgPhfzf-oNj5C_Ss1u39sv54E6N1bsLt45QtNQ2CBDTZ1hIV1I_VXOsufYT8Mfi-A`,
      },
    });
    return next.handle(request);
  }

}
