import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Consultant } from './consultant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Consultant>;

@Injectable()
export class ConsultantService {

    private resourceUrl =  SERVER_API_URL + 'api/consultants';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(consultant: Consultant): Observable<EntityResponseType> {
        const copy = this.convert(consultant);
        return this.http.post<Consultant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(consultant: Consultant): Observable<EntityResponseType> {
        const copy = this.convert(consultant);
        return this.http.put<Consultant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Consultant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Consultant[]>> {
        const options = createRequestOption(req);
        return this.http.get<Consultant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Consultant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Consultant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Consultant[]>): HttpResponse<Consultant[]> {
        const jsonResponse: Consultant[] = res.body;
        const body: Consultant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Consultant.
     */
    private convertItemFromServer(consultant: Consultant): Consultant {
        const copy: Consultant = Object.assign({}, consultant);
        copy.dateDebutInterco = this.dateUtils
            .convertLocalDateFromServer(consultant.dateDebutInterco);
        return copy;
    }

    /**
     * Convert a Consultant to a JSON which can be sent to the server.
     */
    private convert(consultant: Consultant): Consultant {
        const copy: Consultant = Object.assign({}, consultant);
        copy.dateDebutInterco = this.dateUtils
            .convertLocalDateToServer(consultant.dateDebutInterco);
        return copy;
    }
}
