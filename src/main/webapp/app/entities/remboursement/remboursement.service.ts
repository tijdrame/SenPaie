import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Remboursement } from './remboursement.model';
import { createRequestOption } from '../../shared';
import {Collaborateur} from "../collaborateur";
import {MembreFamille} from "../membre-famille/membre-famille.model";

export type EntityResponseType = HttpResponse<Remboursement>;

@Injectable()
export class RemboursementService {

    private resourceUrl =  SERVER_API_URL + 'api/remboursements';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(remboursement: Remboursement): Observable<EntityResponseType> {
        const copy = this.convert(remboursement);
        return this.http.post<Remboursement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(remboursement: Remboursement): Observable<EntityResponseType> {
        const copy = this.convert(remboursement);
        return this.http.put<Remboursement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Remboursement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findByCollab(id: Collaborateur): Observable<HttpResponse<Remboursement[]>> {
        return this.http.get<Remboursement[]>(`${this.resourceUrl+"-collab"}/${id}`, { observe: 'response'})
            .map((res: HttpResponse<Remboursement[]>) => this.convertArrayResponse(res));
    }


    query(req?: any): Observable<HttpResponse<Remboursement[]>> {
        const options = createRequestOption(req);
        return this.http.get<Remboursement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Remboursement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Remboursement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Remboursement[]>): HttpResponse<Remboursement[]> {
        const jsonResponse: Remboursement[] = res.body;
        const body: Remboursement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Remboursement.
     */
    private convertItemFromServer(remboursement: Remboursement): Remboursement {
        const copy: Remboursement = Object.assign({}, remboursement);
        copy.dateRemboursement = this.dateUtils
            .convertLocalDateFromServer(remboursement.dateRemboursement);
        return copy;
    }

    /**
     * Convert a Remboursement to a JSON which can be sent to the server.
     */
    private convert(remboursement: Remboursement): Remboursement {
        const copy: Remboursement = Object.assign({}, remboursement);
        copy.dateRemboursement = this.dateUtils
            .convertLocalDateToServer(remboursement.dateRemboursement);
        return copy;
    }
}
