import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Collaborateur } from './collaborateur.model';
import { createRequestOption } from '../../shared';
import {Absence} from "../absence/absence.model";

export type EntityResponseType = HttpResponse<Collaborateur>;

@Injectable()
export class CollaborateurService {

    private resourceUrl =  SERVER_API_URL + 'api/collaborateurs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(collaborateur: Collaborateur): Observable<EntityResponseType> {
        const copy = this.convert(collaborateur);
        return this.http.post<Collaborateur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(collaborateur: Collaborateur): Observable<EntityResponseType> {
        const copy = this.convert(collaborateur);
        return this.http.put<Collaborateur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Collaborateur>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    search(prenom?: string, nom?: string, tel?:string, deleted?:boolean): Observable<HttpResponse<Collaborateur[]>>{
        return this.http.get<Collaborateur[]>(`${this.resourceUrl+"Ter"}/${prenom}/${nom}/${tel}/${deleted}`, { observe: 'response'})
            .map((res: HttpResponse<Collaborateur[]>) => this.convertArrayResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Collaborateur[]>> {
        const options = createRequestOption(req);
        return this.http.get<Collaborateur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Collaborateur[]>) => this.convertArrayResponse(res));
    }

    queryBis(req?: any): Observable<HttpResponse<Collaborateur[]>> {
        const options = createRequestOption(req);
        return this.http.get<Collaborateur[]>(this.resourceUrl+"Bis", { params: options, observe: 'response' })
            .map((res: HttpResponse<Collaborateur[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Collaborateur = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Collaborateur[]>): HttpResponse<Collaborateur[]> {
        const jsonResponse: Collaborateur[] = res.body;
        const body: Collaborateur[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Collaborateur.
     */
    private convertItemFromServer(collaborateur: Collaborateur): Collaborateur {
        const copy: Collaborateur = Object.assign({}, collaborateur);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateFromServer(collaborateur.dateNaissance);
        return copy;
    }

    /**
     * Convert a Collaborateur to a JSON which can be sent to the server.
     */
    private convert(collaborateur: Collaborateur): Collaborateur {
        const copy: Collaborateur = Object.assign({}, collaborateur);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(collaborateur.dateNaissance);
        return copy;
    }
}
