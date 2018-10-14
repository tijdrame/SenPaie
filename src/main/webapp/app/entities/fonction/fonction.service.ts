import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Fonction } from './fonction.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Fonction>;

@Injectable()
export class FonctionService {

    private resourceUrl =  SERVER_API_URL + 'api/fonctions';

    constructor(private http: HttpClient) { }

    create(fonction: Fonction): Observable<EntityResponseType> {
        const copy = this.convert(fonction);
        return this.http.post<Fonction>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(fonction: Fonction): Observable<EntityResponseType> {
        const copy = this.convert(fonction);
        return this.http.put<Fonction>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Fonction>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Fonction[]>> {
        const options = createRequestOption(req);
        return this.http.get<Fonction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Fonction[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Fonction = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Fonction[]>): HttpResponse<Fonction[]> {
        const jsonResponse: Fonction[] = res.body;
        const body: Fonction[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Fonction.
     */
    private convertItemFromServer(fonction: Fonction): Fonction {
        const copy: Fonction = Object.assign({}, fonction);
        return copy;
    }

    /**
     * Convert a Fonction to a JSON which can be sent to the server.
     */
    private convert(fonction: Fonction): Fonction {
        const copy: Fonction = Object.assign({}, fonction);
        return copy;
    }
}
