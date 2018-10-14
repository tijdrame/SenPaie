import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Sexe } from './sexe.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Sexe>;

@Injectable()
export class SexeService {

    private resourceUrl =  SERVER_API_URL + 'api/sexes';

    constructor(private http: HttpClient) { }

    create(sexe: Sexe): Observable<EntityResponseType> {
        const copy = this.convert(sexe);
        return this.http.post<Sexe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sexe: Sexe): Observable<EntityResponseType> {
        const copy = this.convert(sexe);
        return this.http.put<Sexe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Sexe>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Sexe[]>> {
        const options = createRequestOption(req);
        return this.http.get<Sexe[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Sexe[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Sexe = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Sexe[]>): HttpResponse<Sexe[]> {
        const jsonResponse: Sexe[] = res.body;
        const body: Sexe[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Sexe.
     */
    private convertItemFromServer(sexe: Sexe): Sexe {
        const copy: Sexe = Object.assign({}, sexe);
        return copy;
    }

    /**
     * Convert a Sexe to a JSON which can be sent to the server.
     */
    private convert(sexe: Sexe): Sexe {
        const copy: Sexe = Object.assign({}, sexe);
        return copy;
    }
}
