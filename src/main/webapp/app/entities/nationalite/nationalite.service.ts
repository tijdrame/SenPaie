import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Nationalite } from './nationalite.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Nationalite>;

@Injectable()
export class NationaliteService {

    private resourceUrl =  SERVER_API_URL + 'api/nationalites';

    constructor(private http: HttpClient) { }

    create(nationalite: Nationalite): Observable<EntityResponseType> {
        const copy = this.convert(nationalite);
        return this.http.post<Nationalite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nationalite: Nationalite): Observable<EntityResponseType> {
        const copy = this.convert(nationalite);
        return this.http.put<Nationalite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Nationalite>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Nationalite[]>> {
        const options = createRequestOption(req);
        return this.http.get<Nationalite[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Nationalite[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Nationalite = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Nationalite[]>): HttpResponse<Nationalite[]> {
        const jsonResponse: Nationalite[] = res.body;
        const body: Nationalite[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Nationalite.
     */
    private convertItemFromServer(nationalite: Nationalite): Nationalite {
        const copy: Nationalite = Object.assign({}, nationalite);
        return copy;
    }

    /**
     * Convert a Nationalite to a JSON which can be sent to the server.
     */
    private convert(nationalite: Nationalite): Nationalite {
        const copy: Nationalite = Object.assign({}, nationalite);
        return copy;
    }
}
