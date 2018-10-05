import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DemandeConge } from './demande-conge.model';
import { createRequestOption } from '../../shared';
import {Collaborateur} from "../collaborateur/collaborateur.model";

export type EntityResponseType = HttpResponse<DemandeConge>;

@Injectable()
export class DemandeCongeService {

    private resourceUrl =  SERVER_API_URL + 'api/demande-conges';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(demandeConge: DemandeConge): Observable<EntityResponseType> {
        const copy = this.convert(demandeConge);
        return this.http.post<DemandeConge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(demandeConge: DemandeConge): Observable<EntityResponseType> {
        const copy = this.convert(demandeConge);
        return this.http.put<DemandeConge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DemandeConge>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    search(prenom?: string, nom?: string, telephone?:string): Observable<HttpResponse<DemandeConge[]>>{
        return this.http.get<DemandeConge[]>(`${this.resourceUrl+"Ter"}/${prenom}/${nom}/${telephone}/`, { observe: 'response'})
            .map((res: HttpResponse<DemandeConge[]>) => this.convertArrayResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DemandeConge[]>> {
        const options = createRequestOption(req);
        return this.http.get<DemandeConge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DemandeConge[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DemandeConge = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DemandeConge[]>): HttpResponse<DemandeConge[]> {
        const jsonResponse: DemandeConge[] = res.body;
        const body: DemandeConge[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DemandeConge.
     */
    private convertItemFromServer(demandeConge: DemandeConge): DemandeConge {
        const copy: DemandeConge = Object.assign({}, demandeConge);
        copy.dateCreated = this.dateUtils
            .convertLocalDateFromServer(demandeConge.dateCreated);
        copy.dateDebut = this.dateUtils
            .convertLocalDateFromServer(demandeConge.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateFromServer(demandeConge.dateFin);
        return copy;
    }

    /**
     * Convert a DemandeConge to a JSON which can be sent to the server.
     */
    private convert(demandeConge: DemandeConge): DemandeConge {
        const copy: DemandeConge = Object.assign({}, demandeConge);
        copy.dateCreated = this.dateUtils
            .convertLocalDateToServer(demandeConge.dateCreated);
        copy.dateDebut = this.dateUtils
            .convertLocalDateToServer(demandeConge.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateToServer(demandeConge.dateFin);
        return copy;
    }
}
