import { HttpInterceptorFn } from '@angular/common/http';
import { CoockieService } from '../services/coockie.service';
import { ConfigService } from '../services/config.service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

    if (req.url === '/config.json') {
        return next(req);
    }

    const coockieService = inject(CoockieService);
    const configService = inject(ConfigService);

    const apiUrl = configService.apiUrl.replace(/\/+$/, '');

    const isApiRequest =
        req.url === apiUrl ||
        req.url.startsWith(`${apiUrl}/`) ||
        req.url.startsWith(`${apiUrl}?`);

    if (!isApiRequest) {
        return next(req);
    }

    const token = coockieService.getToken();

    if (token) {
        req = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }

    return next(req);
};