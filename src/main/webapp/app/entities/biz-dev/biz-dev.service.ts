import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BizDev } from './biz-dev.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BizDev>;

@Injectable()
export class BizDevService {

    private resourceUrl =  SERVER_API_URL + 'api/biz-devs';

    constructor(private http: HttpClient) { }

    create(bizDev: BizDev): Observable<EntityResponseType> {
        const copy = this.convert(bizDev);
        return this.http.post<BizDev>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bizDev: BizDev): Observable<EntityResponseType> {
        const copy = this.convert(bizDev);
        return this.http.put<BizDev>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BizDev>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BizDev[]>> {
        const options = createRequestOption(req);
        return this.http.get<BizDev[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BizDev[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BizDev = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BizDev[]>): HttpResponse<BizDev[]> {
        const jsonResponse: BizDev[] = res.body;
        const body: BizDev[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BizDev.
     */
    private convertItemFromServer(bizDev: BizDev): BizDev {
        const copy: BizDev = Object.assign({}, bizDev);
        return copy;
    }

    /**
     * Convert a BizDev to a JSON which can be sent to the server.
     */
    private convert(bizDev: BizDev): BizDev {
        const copy: BizDev = Object.assign({}, bizDev);
        return copy;
    }
}
