import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Etat } from './etat.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Etat>;

@Injectable()
export class EtatService {

    private resourceUrl =  SERVER_API_URL + 'api/etats';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(etat: Etat): Observable<EntityResponseType> {
        const copy = this.convert(etat);
        return this.http.post<Etat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(etat: Etat): Observable<EntityResponseType> {
        const copy = this.convert(etat);
        return this.http.put<Etat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Etat>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Etat[]>> {
        const options = createRequestOption(req);
        return this.http.get<Etat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Etat[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Etat = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Etat[]>): HttpResponse<Etat[]> {
        const jsonResponse: Etat[] = res.body;
        const body: Etat[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Etat.
     */
    private convertItemFromServer(etat: Etat): Etat {
        const copy: Etat = Object.assign({}, etat);
        copy.dateDemarrage = this.dateUtils
            .convertDateTimeFromServer(etat.dateDemarrage);
        copy.dateDebutInterco = this.dateUtils
            .convertDateTimeFromServer(etat.dateDebutInterco);
        return copy;
    }

    /**
     * Convert a Etat to a JSON which can be sent to the server.
     */
    private convert(etat: Etat): Etat {
        const copy: Etat = Object.assign({}, etat);

        copy.dateDemarrage = this.dateUtils.toDate(etat.dateDemarrage);

        copy.dateDebutInterco = this.dateUtils.toDate(etat.dateDebutInterco);
        return copy;
    }
}
