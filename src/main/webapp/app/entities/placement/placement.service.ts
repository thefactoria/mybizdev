import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Placement } from './placement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Placement>;

@Injectable()
export class PlacementService {

    private resourceUrl =  SERVER_API_URL + 'api/placements';

    constructor(private http: HttpClient) { }

    create(placement: Placement): Observable<EntityResponseType> {
        const copy = this.convert(placement);
        return this.http.post<Placement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(placement: Placement): Observable<EntityResponseType> {
        const copy = this.convert(placement);
        return this.http.put<Placement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Placement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Placement[]>> {
        const options = createRequestOption(req);
        return this.http.get<Placement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Placement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Placement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Placement[]>): HttpResponse<Placement[]> {
        const jsonResponse: Placement[] = res.body;
        const body: Placement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Placement.
     */
    private convertItemFromServer(placement: Placement): Placement {
        const copy: Placement = Object.assign({}, placement);
        return copy;
    }

    /**
     * Convert a Placement to a JSON which can be sent to the server.
     */
    private convert(placement: Placement): Placement {
        const copy: Placement = Object.assign({}, placement);
        return copy;
    }
}
