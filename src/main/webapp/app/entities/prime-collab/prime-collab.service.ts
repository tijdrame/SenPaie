import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PrimeCollab } from './prime-collab.model';
import { createRequestOption } from '../../shared';
import {Collaborateur} from "../collaborateur";

export type EntityResponseType = HttpResponse<PrimeCollab>;

@Injectable()
export class PrimeCollabService {

    private resourceUrl =  SERVER_API_URL + 'api/prime-collabs';

    constructor(private http: HttpClient) { }

    create(primeCollab: PrimeCollab): Observable<EntityResponseType> {
        const copy = this.convert(primeCollab);
        return this.http.post<PrimeCollab>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(primeCollab: PrimeCollab): Observable<EntityResponseType> {
        const copy = this.convert(primeCollab);
        return this.http.put<PrimeCollab>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findByCollab(id: Collaborateur): Observable<HttpResponse<PrimeCollab[]>> {
        return this.http.get<PrimeCollab[]>(`${this.resourceUrl+"Ter"}/${id}`, { observe: 'response'})
            .map((res: HttpResponse<PrimeCollab[]>) => this.convertArrayResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PrimeCollab>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PrimeCollab[]>> {
        const options = createRequestOption(req);
        return this.http.get<PrimeCollab[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PrimeCollab[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PrimeCollab = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PrimeCollab[]>): HttpResponse<PrimeCollab[]> {
        const jsonResponse: PrimeCollab[] = res.body;
        const body: PrimeCollab[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PrimeCollab.
     */
    private convertItemFromServer(primeCollab: PrimeCollab): PrimeCollab {
        const copy: PrimeCollab = Object.assign({}, primeCollab);
        return copy;
    }

    /**
     * Convert a PrimeCollab to a JSON which can be sent to the server.
     */
    private convert(primeCollab: PrimeCollab): PrimeCollab {
        const copy: PrimeCollab = Object.assign({}, primeCollab);
        return copy;
    }
}
