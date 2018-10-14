import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Regime } from './regime.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Regime>;

@Injectable()
export class RegimeService {

    private resourceUrl =  SERVER_API_URL + 'api/regimes';

    constructor(private http: HttpClient) { }

    create(regime: Regime): Observable<EntityResponseType> {
        const copy = this.convert(regime);
        return this.http.post<Regime>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(regime: Regime): Observable<EntityResponseType> {
        const copy = this.convert(regime);
        return this.http.put<Regime>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Regime>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Regime[]>> {
        const options = createRequestOption(req);
        return this.http.get<Regime[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Regime[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Regime = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Regime[]>): HttpResponse<Regime[]> {
        const jsonResponse: Regime[] = res.body;
        const body: Regime[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Regime.
     */
    private convertItemFromServer(regime: Regime): Regime {
        const copy: Regime = Object.assign({}, regime);
        return copy;
    }

    /**
     * Convert a Regime to a JSON which can be sent to the server.
     */
    private convert(regime: Regime): Regime {
        const copy: Regime = Object.assign({}, regime);
        return copy;
    }
}
