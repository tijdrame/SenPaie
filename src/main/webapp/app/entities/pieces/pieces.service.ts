import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Pieces } from './pieces.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Pieces>;

@Injectable()
export class PiecesService {

    private resourceUrl =  SERVER_API_URL + 'api/pieces';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(pieces: Pieces): Observable<EntityResponseType> {
        const copy = this.convert(pieces);
        return this.http.post<Pieces>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pieces: Pieces): Observable<EntityResponseType> {
        const copy = this.convert(pieces);
        return this.http.put<Pieces>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Pieces>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Pieces[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pieces[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Pieces[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Pieces = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Pieces[]>): HttpResponse<Pieces[]> {
        const jsonResponse: Pieces[] = res.body;
        const body: Pieces[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Pieces.
     */
    private convertItemFromServer(pieces: Pieces): Pieces {
        const copy: Pieces = Object.assign({}, pieces);
        copy.dateDebut = this.dateUtils
            .convertLocalDateFromServer(pieces.dateDebut);
        copy.dateExpiration = this.dateUtils
            .convertLocalDateFromServer(pieces.dateExpiration);
        copy.dateCreated = this.dateUtils
            .convertLocalDateFromServer(pieces.dateCreated);
        copy.dateDeleted = this.dateUtils
            .convertLocalDateFromServer(pieces.dateDeleted);
        copy.dateUpdated = this.dateUtils
            .convertLocalDateFromServer(pieces.dateUpdated);
        return copy;
    }

    /**
     * Convert a Pieces to a JSON which can be sent to the server.
     */
    private convert(pieces: Pieces): Pieces {
        const copy: Pieces = Object.assign({}, pieces);
        copy.dateDebut = this.dateUtils
            .convertLocalDateToServer(pieces.dateDebut);
        copy.dateExpiration = this.dateUtils
            .convertLocalDateToServer(pieces.dateExpiration);
        copy.dateCreated = this.dateUtils
            .convertLocalDateToServer(pieces.dateCreated);
        copy.dateDeleted = this.dateUtils
            .convertLocalDateToServer(pieces.dateDeleted);
        copy.dateUpdated = this.dateUtils
            .convertLocalDateToServer(pieces.dateUpdated);
        return copy;
    }
}
