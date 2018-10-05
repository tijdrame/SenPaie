import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bulletin>;

@Injectable()
export class BulletinService {

    private resourceUrl =  SERVER_API_URL + 'api/bulletins';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(bulletin: Bulletin): Observable<EntityResponseType> {
        const copy = this.convert(bulletin);
        return this.http.post<Bulletin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bulletin: Bulletin): Observable<EntityResponseType> {
        const copy = this.convert(bulletin);
        return this.http.put<Bulletin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bulletin>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bulletin[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bulletin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bulletin[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bulletin = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bulletin[]>): HttpResponse<Bulletin[]> {
        const jsonResponse: Bulletin[] = res.body;
        const body: Bulletin[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bulletin.
     */
    private convertItemFromServer(bulletin: Bulletin): Bulletin {
        const copy: Bulletin = Object.assign({}, bulletin);
        copy.dateCreated = this.dateUtils
            .convertLocalDateFromServer(bulletin.dateCreated);
        copy.dateUpdated = this.dateUtils
            .convertLocalDateFromServer(bulletin.dateUpdated);
        copy.dateDeleted = this.dateUtils
            .convertLocalDateFromServer(bulletin.dateDeleted);
        return copy;
    }

    /**
     * Convert a Bulletin to a JSON which can be sent to the server.
     */
    private convert(bulletin: Bulletin): Bulletin {
        const copy: Bulletin = Object.assign({}, bulletin);
        copy.dateCreated = this.dateUtils
            .convertLocalDateToServer(bulletin.dateCreated);
        copy.dateUpdated = this.dateUtils
            .convertLocalDateToServer(bulletin.dateUpdated);
        copy.dateDeleted = this.dateUtils
            .convertLocalDateToServer(bulletin.dateDeleted);
        return copy;
    }
}
