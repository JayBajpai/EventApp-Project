import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = localStorage.getItem('tokenData');

    const sanitizedToken = token?.replace(/^"(.*)"$/, '$1');
    if (sanitizedToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${sanitizedToken}`
        }
      });
    }

    return next.handle(request);
  }
}
