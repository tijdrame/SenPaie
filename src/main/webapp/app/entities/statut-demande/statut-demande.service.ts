import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { StatutDemande } from './statut-demande.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<StatutDemande>;

@Injectable()
export class StatutDemandeService {

    private resourceUrl =  SERVER_API_URL + 'api/statut-demandes';

    constructor(private http: HttpClient) { }

    create(statutDemande: StatutDemande): Observable<EntityResponseType> {
        const copy = this.convert(statutDemande);
        return this.http.post<StatutDemande>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(statutDemande: StatutDemande): Observable<EntityResponseType> {
        const copy = this.convert(statutDemande);
        return this.http.put<StatutDemande>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<StatutDemande>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<StatutDemande[]>> {
        const options = createRequestOption(req);
        return this.http.get<StatutDemande[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<StatutDemande[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: StatutDemande = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<StatutDemande[]>): HttpResponse<StatutDemande[]> {
        const jsonResponse: StatutDemande[] = res.body;
        const body: StatutDemande[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to StatutDemande.
     */
    private convertItemFromServer(statutDemande: StatutDemande): StatutDemande {
        const copy: StatutDemande = Object.assign({}, statutDemande);
        return copy;
    }

    /**
     * Convert a StatutDemande to a JSON which can be sent to the server.
     */
    private convert(statutDemande: StatutDemande): StatutDemande {
        const copy: StatutDemande = Object.assign({}, statutDemande);
        return copy;
    }
}
