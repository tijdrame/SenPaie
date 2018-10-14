import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MembreFamille } from './membre-famille.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MembreFamille>;

@Injectable()
export class MembreFamilleService {

    private resourceUrl =  SERVER_API_URL + 'api/membre-familles';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(membreFamille: MembreFamille): Observable<EntityResponseType> {
        const copy = this.convert(membreFamille);
        return this.http.post<MembreFamille>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(membreFamille: MembreFamille): Observable<EntityResponseType> {
        const copy = this.convert(membreFamille);
        return this.http.put<MembreFamille>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MembreFamille>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MembreFamille[]>> {
        const options = createRequestOption(req);
        return this.http.get<MembreFamille[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MembreFamille[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MembreFamille = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MembreFamille[]>): HttpResponse<MembreFamille[]> {
        const jsonResponse: MembreFamille[] = res.body;
        const body: MembreFamille[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MembreFamille.
     */
    private convertItemFromServer(membreFamille: MembreFamille): MembreFamille {
        const copy: MembreFamille = Object.assign({}, membreFamille);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateFromServer(membreFamille.dateNaissance);
        copy.dateMariage = this.dateUtils
            .convertLocalDateFromServer(membreFamille.dateMariage);
        return copy;
    }

    /**
     * Convert a MembreFamille to a JSON which can be sent to the server.
     */
    private convert(membreFamille: MembreFamille): MembreFamille {
        const copy: MembreFamille = Object.assign({}, membreFamille);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(membreFamille.dateNaissance);
        copy.dateMariage = this.dateUtils
            .convertLocalDateToServer(membreFamille.dateMariage);
        return copy;
    }
}
