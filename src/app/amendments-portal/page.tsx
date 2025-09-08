'use client';

import { useState } from 'react';
import { ChevronDown, Search, FileText, Calendar, Clock, Download, MessageCircle, User, Tag, Paperclip } from 'lucide-react';

const amendments = [
  {
    id: 1,
    title: 'Draft Companies (Amendment) Rules, 2024',
    publishedDate: '2024-01-15',
    dueDate: '2024-03-15',
    description: 'Proposed amendments to Companies Rules relating to board meetings and director appointments, focusing on enhancing corporate governance and transparency.',
    fullDescription: 'This draft proposes significant changes to the existing Companies Rules. Key amendments include stricter regulations for board meeting procedures, disclosure of director interests, and enhanced responsibilities for independent directors. The goal is to align with international best practices and protect stakeholder interests.',
    pdfLink: '#',
  },
  {
    id: 2,
    title: 'Taxation Laws (Second Amendment) Bill, 2024',
    publishedDate: '2024-02-01',
    dueDate: '2024-04-01',
    description: 'Introduction of new sections to address tax evasion and promote digital transactions. Includes revised TDS rates.',
    fullDescription: 'The Taxation Laws (Second Amendment) Bill, 2024, introduces robust measures to curb tax evasion through enhanced reporting and verification mechanisms. It also provides incentives for digital payments and revises rates for Tax Deducted at Source (TDS) on various transactions.',
    pdfLink: '#',
  },
  {
    id: 3,
    title: 'LLP (Amendment) Rules 2024 - Form Filing Procedures',
    publishedDate: '2024-02-20',
    dueDate: '2024-03-25',
    description: 'Proposed changes to Limited Liability Partnership form filing procedures and compliance requirements.',
    fullDescription: 'These amendments aim to streamline the form-filing process for LLPs, making it faster and more efficient. The changes include simplification of compliance requirements and the introduction of new digital forms to reduce paperwork.',
    pdfLink: '#',
  },
];

const comments = [
    {
        name: 'Rajesh Kumar',
        category: 'Corporate Law',
        date: '2024-03-10',
        message: 'The proposed changes for director appointments are a welcome move. It will bring more accountability.',
      },
      {
        name: 'Anonymous',
        category: 'Taxation',
        date: '2024-03-12',
        message: 'The revised TDS rates seem a bit high for small businesses. Request a review.',
      },
      {
        name: 'Priya Sharma',
        category: 'Compliance',
        date: '2024-03-14',
        message: 'The new filing procedures for LLPs will definitely save time and effort. Good initiative!',
      },
];

export default function AmendmentsCommentsPortal() {
  const [selectedAmendment, setSelectedAmendment] = useState(null);

  const handleViewClick = (amendment) => {
    setSelectedAmendment(amendment);
  };

  const getDaysRemaining = (dueDate) => {
    const due = new Date(dueDate);
    const today = new Date();
    const diffTime = due.getTime() - today.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays > 0 ? diffDays : 0;
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-blue-900 text-white shadow-md">
        <div className="container mx-auto px-6 py-4">
          <h1 className="text-2xl font-bold">Amendments & Comments Portal</h1>
        </div>
      </header>

      <main className="container mx-auto px-6 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2">
            <div className="bg-white p-6 rounded-lg shadow-lg">
              <div className="flex flex-col md:flex-row justify-between items-center mb-6">
                <div className="relative w-full md:w-1/2 mb-4 md:mb-0">
                  <input
                    type="text"
                    placeholder="Search amendments..."
                    className="w-full pl-10 pr-4 py-2 border rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                  <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                </div>
                <div className="flex items-center space-x-4">
                  <span className="text-gray-600">Sort by:</span>
                  <select className="border rounded-lg px-3 py-2 focus:outline-none">
                    <option>Newest</option>
                    <option>Oldest</option>
                    <option>Closing Soon</option>
                  </select>
                </div>
              </div>

              <div className="space-y-6">
                {amendments.map((amendment) => (
                  <div key={amendment.id} className="border p-5 rounded-lg shadow-sm bg-white hover:shadow-md transition-shadow">
                    <h2 className="text-xl font-semibold text-blue-800">{amendment.title}</h2>
                    <div className="flex items-center space-x-4 text-sm text-gray-500 my-3">
                      <div className="flex items-center"><Calendar size={16} className="mr-2" /> Published: {amendment.publishedDate}</div>
                      <div className="flex items-center"><Clock size={16} className="mr-2" /> Due Date: {amendment.dueDate}</div>
                    </div>
                    <p className="text-gray-700 mb-4">{amendment.description}</p>
                    <button
                      onClick={() => handleViewClick(amendment)}
                      className="bg-blue-600 text-white px-5 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center"
                    >
                      <MessageCircle size={18} className="mr-2" /> View & Comment
                    </button>
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="lg:col-span-1">
            {selectedAmendment && (
              <div className="bg-white p-6 rounded-lg shadow-lg sticky top-8">
                <h2 className="text-2xl font-bold text-blue-900 mb-4">{selectedAmendment.title}</h2>
                <div className="flex items-center justify-between text-sm text-gray-600 mb-4">
                    <div className="flex items-center"><Calendar size={16} className="mr-2"/> Published: {selectedAmendment.publishedDate}</div>
                    <div className="flex items-center"><Clock size={16} className="mr-2"/> Due Date: {selectedAmendment.dueDate}</div>
                </div>
                <p className="text-red-500 font-semibold mb-4">⏳ Comments closing in {getDaysRemaining(selectedAmendment.dueDate)} days</p>
                <p className="text-gray-700 mb-4">{selectedAmendment.fullDescription}</p>
                <a href={selectedAmendment.pdfLink} className="text-blue-600 hover:underline flex items-center mb-6">
                  <Download size={18} className="mr-2" /> Download PDF
                </a>

                <div className="border-t pt-6">
                  <h3 className="text-xl font-semibold mb-4">Submit Your Comment</h3>
                  <form className="space-y-4">
                    <input type="text" placeholder="Your Name" className="w-full p-2 border rounded-lg" />
                    <input type="email" placeholder="Your Email" className="w-full p-2 border rounded-lg" />
                    <select className="w-full p-2 border rounded-lg">
                      <option>Select Category</option>
                      <option>Corporate Law</option>
                      <option>Taxation</option>
                      <option>Compliance</option>
                      <option>Startups</option>
                      <option>Investor Protection</option>
                    </select>
                    <textarea placeholder="Your Comment" rows="4" className="w-full p-2 border rounded-lg"></textarea>
                    <div className="flex items-center space-x-2">
                        <input type="checkbox" id="anonymous" className="rounded"/>
                        <label htmlFor="anonymous">Submit as Anonymous</label>
                    </div>
                    <div className="flex items-center space-x-2">
                        <Paperclip size={16}/>
                        <input type="file" className="text-sm"/>
                    </div>
                    <button type="submit" className="w-full bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition-colors">Submit Comment</button>
                  </form>
                </div>

                <div className="border-t mt-6 pt-6">
                    <h3 className="text-xl font-semibold mb-4">Recent Comments</h3>
                    <div className="space-y-4">
                        {comments.map((comment, index) => (
                            <div key={index} className="bg-gray-100 p-4 rounded-lg">
                                <div className="flex items-center justify-between mb-2">
                                    <div className="flex items-center font-semibold"><User size={16} className="mr-2"/> {comment.name}</div>
                                    <div className="text-xs text-gray-500 flex items-center"><Tag size={12} className="mr-1"/>{comment.category}</div>
                                </div>
                                <p className="text-gray-700">{comment.message}</p>
                                <div className="text-right text-xs text-gray-500 mt-2">{comment.date}</div>
                            </div>
                        ))}
                    </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </main>
    </div>
  );
}
